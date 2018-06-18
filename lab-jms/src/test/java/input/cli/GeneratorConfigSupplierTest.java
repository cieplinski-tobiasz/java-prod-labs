package input.cli;

import input.parsers.Parser;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.core.env.Environment;
import utils.ranges.DateTimeRange;
import utils.ranges.PositiveIntegerRange;
import utils.ranges.Range;

import java.nio.file.Path;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GeneratorConfigSupplierTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Environment environment;

    @Mock
    private Parser<Range<Integer>> intRangeParser;

    @Mock
    private Parser<Range<ZonedDateTime>> dateTimeParser;

    @Mock
    private Parser<Path> pathParser;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenEnvironmentIsNull() {
        Throwable thrown = catchThrowable(() ->
                new GeneratorConfigSupplier(null, intRangeParser, dateTimeParser, pathParser));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenIntRangeParserIsNull() {
        Throwable thrown = catchThrowable(() ->
                new GeneratorConfigSupplier(environment, null, dateTimeParser, pathParser));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenDateTimeRangeParserIsNull() {
        Throwable thrown = catchThrowable(() ->
                new GeneratorConfigSupplier(environment, intRangeParser, null, pathParser));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenPathParserIsNull() {
        Throwable thrown = catchThrowable(() ->
                new GeneratorConfigSupplier(environment, intRangeParser, dateTimeParser, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGetCallEnvironmentSixTimesToLookForValues() {
        when(intRangeParser.parse(any())).thenReturn(mock(PositiveIntegerRange.class));
        when(pathParser.parse(any())).thenReturn(mock(Path.class));
        when(environment.getProperty("eventsCount", "100")).thenReturn("1");
        when(environment.getProperty("itemsFile", "items.csv")).thenReturn("default");
        GeneratorConfigSupplier uut =
                new GeneratorConfigSupplier(environment, intRangeParser, dateTimeParser, pathParser);

        uut.get();

        verify(environment, times(6)).getProperty(anyString(), anyString());
    }

    @Test
    public void shouldGetCallEnvironmentGetPropertyWhenEnvironmentContainsDate() {
        when(intRangeParser.parse(any())).thenReturn(mock(PositiveIntegerRange.class));
        when(pathParser.parse(any())).thenReturn(mock(Path.class));
        when(environment.getProperty("eventsCount", "100")).thenReturn("1");
        when(dateTimeParser.parse(any())).thenReturn(mock(DateTimeRange.class));
        when(environment.getProperty("itemsFile", "items.csv")).thenReturn("default");
        when(environment.containsProperty("dateRange")).thenReturn(true);
        when(environment.getProperty("dateRange")).thenReturn("date range");
        GeneratorConfigSupplier uut =
                new GeneratorConfigSupplier(environment, intRangeParser, dateTimeParser, pathParser);

        uut.get();

        verify(environment, times(1)).getProperty("dateRange");
    }

    @Test
    public void shouldGetCallDateTimeParserWhenEnvironmentContainsDate() {
        when(intRangeParser.parse(any())).thenReturn(mock(PositiveIntegerRange.class));
        when(pathParser.parse(any())).thenReturn(mock(Path.class));
        when(environment.getProperty("eventsCount", "100")).thenReturn("1");
        when(dateTimeParser.parse(any())).thenReturn(mock(DateTimeRange.class));
        when(environment.getProperty("itemsFile", "items.csv")).thenReturn("default");
        when(environment.containsProperty("dateRange")).thenReturn(true);
        when(environment.getProperty("dateRange")).thenReturn("date range");
        GeneratorConfigSupplier uut =
                new GeneratorConfigSupplier(environment, intRangeParser, dateTimeParser, pathParser);

        uut.get();

        verify(dateTimeParser, times(1)).parse("date range");
    }
}