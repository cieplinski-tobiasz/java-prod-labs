package utils.ranges;

import org.junit.Test;
import utils.validators.config.GeneratorConfigValidator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DateTimeRangeTest {
    @Test
    public void shouldThrowNullPointerExceptionWhenFromIsNull() {
        ZonedDateTime to = ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("Z"));

        Throwable thrown = catchThrowable(() -> DateTimeRange.between(null, to));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenToIsNull() {
        ZonedDateTime from = ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("Z"));

        Throwable thrown = catchThrowable(() -> DateTimeRange.between(from, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenGeneratorConfigIsNull() {
        GeneratorConfigValidator uut = new GeneratorConfigValidator();

        Throwable thrown = catchThrowable(() -> uut.setGeneratorConfig(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFromEqualsTo() {
        ZonedDateTime from = ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("Z"));

        Throwable thrown = catchThrowable(() -> DateTimeRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFromIsGreaterThanTo() {
        ZonedDateTime from = ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(LocalDateTime.MIN, ZoneId.of("Z"));

        Throwable thrown = catchThrowable(() -> DateTimeRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotThrowExceptionsWhenParametersAreValid() {
        ZonedDateTime from = ZonedDateTime.of(LocalDateTime.MIN, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("Z"));

        Throwable thrown = catchThrowable(() -> DateTimeRange.between(from, to));

        assertThat(thrown).doesNotThrowAnyException();
    }

    @Test
    public void shouldInReturnTrueWhenInRange() {
        ZonedDateTime from = ZonedDateTime.of(1999, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2001, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime in = ZonedDateTime.of(2000, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        DateTimeRange uut = DateTimeRange.between(from, to);

        boolean result = uut.in(in);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldInReturnTrueWhenEqualToFrom() {
        ZonedDateTime from = ZonedDateTime.of(1999, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2001, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        DateTimeRange uut = DateTimeRange.between(from, to);

        boolean result = uut.in(from);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldInReturnFalseWhenBelowRange() {
        ZonedDateTime from = ZonedDateTime.of(1999, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2000, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime in = ZonedDateTime.of(1980, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        DateTimeRange uut = DateTimeRange.between(from, to);

        boolean result = uut.in(in);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldInReturnFalseWhenOverRange() {
        ZonedDateTime from = ZonedDateTime.of(1999, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2001, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime in = ZonedDateTime.of(2005, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        DateTimeRange uut = DateTimeRange.between(from, to);

        boolean result = uut.in(in);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldGetMinimumReturnFrom() {
        ZonedDateTime from = ZonedDateTime.of(1999, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2001, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        DateTimeRange uut = DateTimeRange.between(from, to);

        ZonedDateTime result = uut.getMinimum();

        assertThat(result).isEqualTo(from);
    }

    @Test
    public void shouldGetMaximumReturnTo() {
        ZonedDateTime from = ZonedDateTime.of(1999, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2001, 6, 6, 6, 30, 30, 30, ZoneId.of("Z"));
        DateTimeRange uut = DateTimeRange.between(from, to);

        ZonedDateTime result = uut.getMaximum();

        assertThat(result).isEqualTo(to);
    }

}