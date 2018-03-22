package utils.validators.config;

import config.generator.GeneratorConfig;

import java.util.Objects;

public class GeneratorConfigValidator {
    private GeneratorConfig generatorConfig;

    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        Objects.requireNonNull(generatorConfig);
        this.generatorConfig = generatorConfig;
    }

    public void validate() {
        checkEventsCount();
        checkOutputDirectory();
    }

    private void checkEventsCount() {
        if (generatorConfig.getEventsCount() <= 0) {
            throw new IllegalStateException("Events count must be greater than 0.");
        }
    }

    private void checkOutputDirectory() {
        if (!generatorConfig.getOutDir().toFile().isDirectory()) {
            throw new IllegalStateException("Given output path is not a directory.");
        }
    }
}
