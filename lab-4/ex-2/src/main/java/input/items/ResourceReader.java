package input.items;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.stream.Stream;

@Slf4j
@Component
public final class ResourceReader {
    public Stream<String> getResourceLines(@NonNull String filename) {
        BufferedReader bufferedReader = getReader(filename);
        return readLines(bufferedReader);
    }

    private BufferedReader getReader(String filename) {
        return new BufferedReader(new InputStreamReader(ResourceReader.class.getResourceAsStream("/" + filename)));
    }

    private Stream<String> readLines(BufferedReader bufferedReader) {
        try {
            return bufferedReader.lines();
        } catch (UncheckedIOException e) {
            logException(e);
            throw e;
        }
    }

    private void logException(UncheckedIOException e) {
        IOException cause = e.getCause();

        if (cause instanceof FileNotFoundException) {
            log.error("Item file does not exist.", cause);
        } else if (cause instanceof AccessDeniedException) {
            log.error("Access to item file denied.", cause);
        } else {
            log.error("Could not open items file.", cause);
        }
    }
}
