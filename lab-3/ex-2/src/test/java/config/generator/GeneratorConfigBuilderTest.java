package config.generator;


import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.validators.config.GeneratorConfigValidator;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doThrow;

public class GeneratorConfigBuilderTest {
    @Mock
    private GeneratorConfigValidator generatorConfigValidator;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldBuildDefaultGeneratorConfig() {
        GeneratorConfigBuilder uut = new GeneratorConfigBuilder(generatorConfigValidator);

        GeneratorConfig result = uut.build();

        assertThat(result.getEventsCount()).isEqualTo(100);
        assertThat(result.getOutDir()).isDirectory();
    }

    @Test
    public void shouldBuildCustomGeneratorConfig() {
        GeneratorConfigBuilder uut = new GeneratorConfigBuilder(generatorConfigValidator);

        GeneratorConfig result = uut.withEventsCount(2).withOutputDirectory(Paths.get("/usr/")).build();

        assertThat(result.getEventsCount()).isEqualTo(2);
        assertThat(result.getOutDir()).isDirectory();
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenValidationFails() {
        doThrow(IllegalStateException.class).when(generatorConfigValidator).validate();
        GeneratorConfigBuilder uut = new GeneratorConfigBuilder(generatorConfigValidator);

        Throwable thrown = catchThrowable(uut::build);

        assertThat(thrown).isInstanceOf(IllegalStateException.class);
    }

}