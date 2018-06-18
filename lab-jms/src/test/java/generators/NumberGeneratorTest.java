package generators;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NumberGeneratorTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private ThreadLocalRandom random;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenRandomIsNull() {
        Throwable result = catchThrowable(() -> new NumberGenerator(null));

        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldNextIntCallThreadLocalRandomWithAdjustedArguments() {
        NumberGenerator uut = new NumberGenerator(random);

        uut.nextInt(0);

        verify(random, times(1)).nextInt(1);
    }

    @Test
    public void shouldNextIntWithTwoArgumentsCallThreadLocalRandomWithAdjustedArguments() {
        NumberGenerator uut = new NumberGenerator(random);

        uut.nextInt(0, 1);

        verify(random, times(1)).nextInt(0, 2);
    }

    @Test
    public void shouldNextLongCallThreadLocalRandomWithAdjustedArguments() {
        NumberGenerator uut = new NumberGenerator(random);

        uut.nextLong(0, 1);

        verify(random, times(1)).nextLong(0, 2);
    }
}