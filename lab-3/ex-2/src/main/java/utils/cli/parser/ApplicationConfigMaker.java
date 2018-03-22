package utils.cli.parser;

import config.ApplicationConfig;
import config.generator.GeneratorConfig;
import config.generator.GeneratorConfigBuilder;
import config.item.ItemConfig;
import config.item.ItemConfigBuilder;
import config.purchase.PurchaseConfig;
import config.purchase.PurchaseConfigBuilder;
import config.transaction.TransactionConfig;
import config.transaction.TransactionConfigBuilder;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ranges.DateTimeRange;
import utils.ranges.PositiveIntegerRange;
import utils.ranges.Range;
import utils.validators.config.GeneratorConfigValidator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ApplicationConfigMaker {
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfigMaker.class);

    private CommandLine commandLine;
    private GeneratorConfigValidator generatorConfigValidator;

    public ApplicationConfigMaker(CommandLine commandLine, GeneratorConfigValidator generatorConfigValidator) {
        this.commandLine = commandLine;
        this.generatorConfigValidator = generatorConfigValidator;
    }

    public ApplicationConfig getApplicationConfig() {
        return new ApplicationConfig(
                getGeneratorConfig(),
                getItemConfig(),
                getPurchaseConfig(),
                getTransactionConfig()
        );
    }

    private GeneratorConfig getGeneratorConfig() {
        GeneratorConfigBuilder builder = new GeneratorConfigBuilder(generatorConfigValidator);

        parseEventsCount().ifPresent(builder::withEventsCount);
        parseOutputDirectory().ifPresent(builder::withOutputDirectory);

        return builder.build();
    }

    private Optional<Integer> parseEventsCount() {
        String eventsCount = commandLine.getOptionValue("eventsCount");

        if (eventsCount == null) {
            logger.debug("Using default events count");
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(eventsCount));
        } catch (NumberFormatException e) {
            logger.info("Events count parse error.", e);
            throw new IllegalStateException("Cannot parse events count.", e);
        }
    }

    private Optional<Path> parseOutputDirectory() {
        String outDir = commandLine.getOptionValue("outDir");

        if (outDir == null) {
            logger.debug("Using default output directory.");
            return Optional.empty();
        } else {
            return Optional.of(Paths.get(outDir));
        }
    }

    private ItemConfig getItemConfig() {
        ItemConfigBuilder builder = new ItemConfigBuilder();

        parseItemsFilename().ifPresent(builder::withItemsFilename);

        return builder.build();
    }

    private Optional<String> parseItemsFilename() {
        String itemsFile = commandLine.getOptionValue("itemsFile");

        if (itemsFile == null) {
            logger.debug("Using default itemsFile");
            return Optional.empty();
        } else {
            return Optional.of(itemsFile);
        }
    }

    private PurchaseConfig getPurchaseConfig() {
        PurchaseConfigBuilder builder = new PurchaseConfigBuilder();

        parseQuantityRange().ifPresent(builder::withQuantityRange);

        return builder.build();
    }

    private Optional<Range<Integer>> parseQuantityRange() {
        String[] values = commandLine.getOptionValues("itemsQuantity");
        return parseIntegerRange(values, "quantity range");
    }

    private Optional<Range<Integer>> parseIntegerRange(String[] values, String rangeName) {
        if (values == null) {
            logger.debug("Using default {}", rangeName);
            return Optional.empty();
        }

        try {
            int from = Integer.valueOf(values[0]);
            int to = Integer.valueOf(values[1]);
            return Optional.of(PositiveIntegerRange.between(from, to));
        } catch (NumberFormatException e) {
            logger.info("Parsing integer range failed.", e);
            throw new IllegalStateException("Could not parse " + rangeName, e);
        }
    }

    private TransactionConfig getTransactionConfig() {
        TransactionConfigBuilder builder = new TransactionConfigBuilder();

        parseCountRange().ifPresent(builder::withCountRange);
        parseCustomerIdRange().ifPresent(builder::withCustomerIdRange);
        parseDateTimeRange().ifPresent(builder::withDateTimeRange);

        return builder.build();
    }

    private Optional<Range<Integer>> parseCountRange() {
        String[] values = commandLine.getOptionValues("itemsCount");
        return parseIntegerRange(values, "count range");
    }

    private Optional<Range<Integer>> parseCustomerIdRange() {
        String[] values = commandLine.getOptionValues("customerIds");
        return parseIntegerRange(values, "customer id range");
    }

    private Optional<Range<ZonedDateTime>> parseDateTimeRange() {
        String[] values = commandLine.getOptionValues("dateRange");

        if (values == null) {
            logger.debug("Using default date range");
            return Optional.empty();
        }

        for (String value : values) {
            if (value.length() != 30) {
                logger.info("DateTime argument length not equal to 30.");
                throw new IllegalStateException("Bad date time format.");
            }
        }

        for (int i = 0; i< values.length; ++i) {
            values[i] = values[i].trim().substring(1, values[i].length() - 1);
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            ZonedDateTime from = ZonedDateTime.parse(values[0], format);
            ZonedDateTime to = ZonedDateTime.parse(values[1], format);
            return Optional.of(DateTimeRange.between(from, to));
        } catch (DateTimeParseException e) {
            logger.info("DateTime parse failed.", e);
            throw new IllegalStateException("Could not parse date time range", e);
        }
    }

}
