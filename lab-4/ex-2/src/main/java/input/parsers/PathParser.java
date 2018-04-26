package input.parsers;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public final class PathParser implements Parser<Path> {
    @Override
    public Path parse(String value) {
        return Paths.get(value);
    }
}
