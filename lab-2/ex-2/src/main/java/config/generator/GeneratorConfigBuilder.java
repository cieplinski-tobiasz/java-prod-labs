package config.generator;

import utils.validators.config.GeneratorConfigValidator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratorConfigBuilder {
    private GeneratorConfigValidator validator;

    int eventsCount;
    Path outDir;

    public GeneratorConfigBuilder(GeneratorConfigValidator validator) {
        this.validator = validator;
        this.eventsCount = 100;
        this.outDir = Paths.get("").toAbsolutePath();
    }

    public GeneratorConfigBuilder withEventsCount(int eventsCount) {
        this.eventsCount = eventsCount;
        return this;
    }

    public GeneratorConfigBuilder withOutputDirectory(Path outDir) {
        this.outDir = outDir;
        return this;
    }

    public GeneratorConfig build() {
        GeneratorConfig generatorConfig = new GeneratorConfig(this);
        validate(generatorConfig);
        return generatorConfig;
    }

    private void validate(GeneratorConfig generatorConfig) {
        validator.setGeneratorConfig(generatorConfig);
        validator.validate();
    }
}