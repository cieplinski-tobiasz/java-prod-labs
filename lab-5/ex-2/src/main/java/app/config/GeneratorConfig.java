package app.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
@Getter
public final class GeneratorConfig {
    @NonNull
    private final RangesDTO ranges;
    @NonNull
    private final String itemsFilename;
    private final int eventsCount;
    @NonNull
    private final Path outputDirectory;
}
