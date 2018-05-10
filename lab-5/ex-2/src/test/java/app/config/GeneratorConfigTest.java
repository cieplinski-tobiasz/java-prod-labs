package app.config;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GeneratorConfigTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private RangesDTO rangesDTO;

    @Mock
    private Path path;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenRangesDtoIsNull() {
        Throwable thrown = catchThrowable(() -> new GeneratorConfig(null, "", 1, path));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenItemFilenameIsNull() {
        Throwable thrown = catchThrowable(() -> new GeneratorConfig(rangesDTO, null, 1, path));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenPathIsNull() {
        Throwable thrown = catchThrowable(() -> new GeneratorConfig(rangesDTO, "", 1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

}