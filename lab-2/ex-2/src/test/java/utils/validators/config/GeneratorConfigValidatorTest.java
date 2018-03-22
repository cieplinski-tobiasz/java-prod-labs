package utils.validators.config;

import config.generator.GeneratorConfig;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GeneratorConfigValidatorTest {
    @Mock
    private GeneratorConfig generatorConfig;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowNullPointerExceptionWhenGeneratorConfigIsNull() {
        GeneratorConfigValidator uut = new GeneratorConfigValidator();

        Throwable thrown = catchThrowable(() -> uut.setGeneratorConfig(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenOutDirIsNotADirectory() {
        givenGeneratorConfigMockWithOutDirNotADirectory();
        GeneratorConfigValidator uut = new GeneratorConfigValidator();
        uut.setGeneratorConfig(generatorConfig);

        Throwable thrown = catchThrowable(uut::validate);

        assertThat(thrown).isInstanceOf(IllegalStateException.class);
    }

    private void givenGeneratorConfigMockWithOutDirNotADirectory() {
        Mockito.when(generatorConfig.getEventsCount()).thenReturn(1);
        Path pathMock = getPathMockWithFile(false);
        Mockito.when(generatorConfig.getOutDir()).thenReturn(pathMock);
    }


    private Path getPathMockWithFile(boolean fileIsDirectory) {
        File fileMock = getFileMock(fileIsDirectory);
        Path mock = Mockito.mock(Path.class);
        Mockito.when(mock.toFile()).thenReturn(fileMock);

        return mock;
    }

    private File getFileMock(boolean isDirectory) {
        File mock = Mockito.mock(File.class);
        Mockito.when(mock.isDirectory()).thenReturn(isDirectory);

        return mock;
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenEventsCountIsNotPositive() {
        givenGeneratorConfigMockWithNotPositiveEventsCount();
        GeneratorConfigValidator uut = new GeneratorConfigValidator();
        uut.setGeneratorConfig(generatorConfig);

        Throwable thrown = catchThrowable(uut::validate);

        assertThat(thrown).isInstanceOf(IllegalStateException.class);
    }

    private void givenGeneratorConfigMockWithNotPositiveEventsCount() {
        Mockito.when(generatorConfig.getEventsCount()).thenReturn(0);
        Path pathMock = getPathMockWithFile(true);
        Mockito.when(generatorConfig.getOutDir()).thenReturn(pathMock);
    }

    @Test
    public void shouldNotThrowIllegalStateExceptionWhenFieldsAreValid() {
        givenValidGeneratorConfigMock();
        GeneratorConfigValidator uut = new GeneratorConfigValidator();
        uut.setGeneratorConfig(generatorConfig);

        Throwable thrown = catchThrowable(uut::validate);

        assertThat(thrown).doesNotThrowAnyException();
    }

    private void givenValidGeneratorConfigMock() {
        Mockito.when(generatorConfig.getEventsCount()).thenReturn(1);
        Path pathMock = getPathMockWithFile(true);
        Mockito.when(generatorConfig.getOutDir()).thenReturn(pathMock);
    }
}