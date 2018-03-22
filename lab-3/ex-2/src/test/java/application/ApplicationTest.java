package application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ApplicationTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void applicationWorksWellAndThrowsNoExceptionsIntegrationTest() throws IOException {
        File file = folder.newFolder();

        Throwable thrown = catchThrowable(() -> Application.main(new String[]{"-outDir", file.toPath().toString()}));

        assertThat(thrown).doesNotThrowAnyException();
        assertThat(file.listFiles()).hasSize(100);
    }

    @Test
    public void applicationFailsWithWrongParamAndDoesNotThrowRuntimeExceptionIntegrationTest() throws IOException {
        Throwable thrown = catchThrowable(() -> Application.main(new String[]{"-outDir", "no way it's good path"}));

        assertThat(thrown).doesNotThrowAnyException();
    }

}