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

public class XmlConditionTest {
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
        XmlCondition uut = new XmlCondition();

        Throwable thrown = catchThrowable(() -> uut.matches(null, annotatedTypeMetadata));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldMatchesReturnTrueWhenFormatIsPresentAndEqualToXml() {
        when(environment.getProperty("format")).thenReturn("xml");
        when(conditionContext.getEnvironment()).thenReturn(environment);
        XmlCondition uut = new XmlCondition();

        boolean result = uut.matches(conditionContext, annotatedTypeMetadata);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldMatchesReturnFalseWhenFormatIsNotPresent() {
        when(environment.getProperty("format")).thenReturn(null);
        when(conditionContext.getEnvironment()).thenReturn(environment);
        XmlCondition uut = new XmlCondition();

        boolean result = uut.matches(conditionContext, annotatedTypeMetadata);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldMatchesReturnFalseWhenFormatIsPresentAndNotEqualToXml() {
        when(environment.getProperty("format")).thenReturn("notXml");
        when(conditionContext.getEnvironment()).thenReturn(environment);
        XmlCondition uut = new XmlCondition();

        boolean result = uut.matches(conditionContext, annotatedTypeMetadata);

        assertThat(result).isFalse();
    }
}