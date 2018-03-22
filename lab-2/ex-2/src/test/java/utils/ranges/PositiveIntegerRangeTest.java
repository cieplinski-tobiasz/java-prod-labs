package utils.ranges;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PositiveIntegerRangeTest {
    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFromIsNegative() {
        int from = -1;
        int to = 2;

        Throwable thrown = catchThrowable(() -> PositiveIntegerRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenToIsNegative() {
        int from = 5;
        int to = -2;

        Throwable thrown = catchThrowable(() -> PositiveIntegerRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void shouldThrowIllegalArgumentExceptionBothArgumentsAreNotPositive() {
        int from = -3;
        int to = -5;

        Throwable thrown = catchThrowable(() -> PositiveIntegerRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionBothArgumentsAreZero() {
        int from = 0;
        int to = 0;

        Throwable thrown = catchThrowable(() -> PositiveIntegerRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFromEqualsTo() {
        int from = 2;
        int to = 2;

        Throwable thrown = catchThrowable(() -> PositiveIntegerRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFromIsGreaterThanTo() {
        int from = 5;
        int to = 2;

        Throwable thrown = catchThrowable(() -> PositiveIntegerRange.between(from, to));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotThrowExceptionsWhenParametersAreValid() {
        int from = 1;
        int to = 4;

        Throwable notThrown = catchThrowable(() -> PositiveIntegerRange.between(from, to));

        assertThat(notThrown).doesNotThrowAnyException();
    }

    @Test
    public void shouldInReturnTrueWhenInRange() {
        int from = 1;
        int to = 4;
        int in = 2;
        PositiveIntegerRange uut = PositiveIntegerRange.between(from, to);

        boolean result = uut.in(in);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldInReturnFalseWhenBelowRange() {
        int from = 2;
        int to = 5;
        int in = 1;
        PositiveIntegerRange uut = PositiveIntegerRange.between(from, to);

        boolean result = uut.in(in);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldInReturnFalseWhenOverRange() {
        int from = 2;
        int to = 5;
        int in = 7;
        PositiveIntegerRange uut = PositiveIntegerRange.between(from, to);

        boolean result = uut.in(in);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldGetMinimumReturnFrom() {
        int from = 1;
        int to = 2;
        PositiveIntegerRange uut = PositiveIntegerRange.between(from, to);

        Integer result = uut.getMinimum();

        assertThat(result).isEqualTo(from);
    }

    @Test
    public void shouldGetMaximumReturnTo() {
        int from = 1;
        int to = 2;
        PositiveIntegerRange uut = PositiveIntegerRange.between(from, to);

        Integer result = uut.getMaximum();

        assertThat(result).isEqualTo(to);
    }
}