package input.items;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
@Component
public final class ResourceReader {
    public Stream<String> getResourceLines(@NonNull String filepath) {
        Path path = Paths.get(filepath);
        return readLines(path);
    }

    private Stream<String> readLines(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            logException(e);
            throw new UncheckedIOException(e);
        }
    }

    private void logException(IOException e) {
        if (e instanceof FileNotFoundException) {
            log.error("Item file does not exist.", e);
        } else if (e instanceof AccessDeniedException) {
            log.error("Access to item file denied.", e);
        } else {
            log.error("Could not open items file.", e);
        }
    }
}
