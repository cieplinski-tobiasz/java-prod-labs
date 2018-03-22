package config.generator;

import java.nio.file.Path;

public class GeneratorConfig {
    private final int eventsCount;
    private final Path outDir;

    GeneratorConfig(GeneratorConfigBuilder generatorConfigBuilder) {
        this.eventsCount = generatorConfigBuilder.eventsCount;
        this.outDir = generatorConfigBuilder.outDir;
    }

    public int getEventsCount() {
        return eventsCount;
    }

    public Path getOutDir() {
        return outDir;
    }
}
