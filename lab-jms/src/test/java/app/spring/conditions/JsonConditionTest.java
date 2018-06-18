package app.spring.conditions;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class JsonConditionTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ConditionContext conditionContext;

    @Mock
    private Environment environment;

    @Mock
    private AnnotatedTypeMetadata annotatedTypeMetadata;

    @Test
    public void shouldMatchesThrowNullPointerExceptionGivenConditionContextIsNull() {
        JsonCondition uut = new JsonCondition();

        Throwable thrown = catchThrowable(() -> uut.matches(null, annotatedTypeMetadata));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldMatchesReturnTrueWhenFormatIsNotPresent() {
        when(environment.getProperty("format")).thenReturn(null);
        when(conditionContext.getEnvironment()).thenReturn(environment);
        JsonCondition uut = new JsonCondition();

        boolean result = uut.matches(conditionContext, annotatedTypeMetadata);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldMatchesReturnTrueWhenFormatIsPresentAndEqualToJson() {
        when(environment.getProperty("format")).thenReturn("json");
        when(conditionContext.getEnvironment()).thenReturn(environment);
        JsonCondition uut = new JsonCondition();

        boolean result = uut.matches(conditionContext, annotatedTypeMetadata);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldMatchesReturnFalseWhenFormatIsPresentAndNotEqualToJson() {
        when(environment.getProperty("format")).thenReturn("notJson");
        when(conditionContext.getEnvironment()).thenReturn(environment);
        JsonCondition uut = new JsonCondition();

        boolean result = uut.matches(conditionContext, annotatedTypeMetadata);

        assertThat(result).isFalse();
    }
}