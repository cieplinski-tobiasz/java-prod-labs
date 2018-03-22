package utils.validators.config;

import config.generator.GeneratorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.cli.InputParser;

import java.util.Objects;

public class GeneratorConfigValidator {
    private static Logger logger = LoggerFactory.getLogger(GeneratorConfigValidator.class);

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
            logger.info("Not positive events count");
            throw new IllegalStateException("Events count must be greater than 0.");
        }
    }

    private void checkOutputDirectory() {
        if (!generatorConfig.getOutDir().toFile().isDirectory()) {
            logger.info("Output path is not a directory.");
            throw new IllegalStateException("Given output path is not a directory.");
        }
    }
}
