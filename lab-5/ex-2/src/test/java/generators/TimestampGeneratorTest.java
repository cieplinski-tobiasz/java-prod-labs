package generators;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.ranges.DateTimeRange;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TimestampGeneratorTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private NumberGenerator numberGenerator;

    @Mock
    private DateTimeRange range;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenNumberGeneratorIsNull() {
        Throwable result = catchThrowable(() -> new TimestampGenerator(null));

        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGenerateTimestampThrowNullPointerExceptionWhenDateRangeIsNull() {
        TimestampGenerator uut = new TimestampGenerator(numberGenerator);

        Throwable result = catchThrowable(() -> uut.generateTimestamp(null));

        assertThat(result).isInstanceOf(NullPointerException.class);
    }
    @Test
    public void shouldGenerateTimestampResultBeInTimestampInRange() {
        ZoneId zoneId = ZoneId.of("Z");
        ZonedDateTime from = ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), zoneId);
        ZonedDateTime to = ZonedDateTime.ofInstant(Instant.ofEpochSecond(2), zoneId);
        when(range.getMinimum()).thenReturn(from);
        when(range.getMaximum()).thenReturn(to);
        ZonedDateTime expected = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1), zoneId);
        when(numberGenerator.nextLong(anyLong(), anyLong())).thenReturn(1L);
        TimestampGenerator uut = new TimestampGenerator(numberGenerator);

        ZonedDateTime result = uut.generateTimestamp(range);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldGenerateTimestampResultBeTheSameAsFromGivenFromEqualsTo() {
        ZoneId zoneId = ZoneId.of("Z");
        ZonedDateTime from = ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), zoneId);
        ZonedDateTime to = from;
        when(range.getMinimum()).thenReturn(from);
        when(range.getMaximum()).thenReturn(to);
        when(numberGenerator.nextLong(anyLong(), anyLong())).thenReturn(0L);
        TimestampGenerator uut = new TimestampGenerator(numberGenerator);

        ZonedDateTime result = uut.generateTimestamp(range);

        assertThat(result).isEqualTo(from);
    }

}