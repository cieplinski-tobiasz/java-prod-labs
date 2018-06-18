package output.file;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public final class FilesWrapper {
    public void write(Path path, byte[] bytes) throws IOException {
        Files.write(path, bytes);
    }

    public void createDirectory(Path path) throws IOException {
        Files.createDirectory(path);
    }

    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    public boolean notExists(Path path) {
        return Files.notExists(path);
    }
}
