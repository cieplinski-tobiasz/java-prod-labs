package application;

import config.ApplicationConfig;
import config.generator.GeneratorConfig;
import json.gson.factories.CustomAdaptersGsonFactory;
import model.Item;
import model.Transaction;
import org.apache.commons.cli.DefaultParser;
import utils.cli.InputParser;
import utils.cli.parser.ApplicationConfigMaker;
import utils.generators.RandomItemGenerator;
import utils.generators.RandomPurchaseGenerator;
import utils.generators.RandomTransactionGenerator;
import utils.parsers.CSVItemParser;
import utils.validators.config.GeneratorConfigValidator;
import utils.writers.TransactionFileWriter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Application {
    private RandomTransactionGenerator randomTransactionGenerator;
    private GeneratorConfig generatorConfig;
    private TransactionFileWriter transactionFileWriter;

    public Application(RandomTransactionGenerator randomTransactionGenerator, GeneratorConfig generatorConfig, TransactionFileWriter transactionFileWriter) {
        this.randomTransactionGenerator = randomTransactionGenerator;
        this.generatorConfig = generatorConfig;
        this.transactionFileWriter = transactionFileWriter;
    }

    private void run() {
        transactionFileWriter.writeTransactions(
                randomTransactionGenerator.generateTransactions(generatorConfig.getEventsCount())
        );
    }

    public static void main(String[] args) {
        runAndPrintErrors(args);
    }

    private static Application getApplicationWithInjectedDependencies(String[] args) {
            InputParser inputParser = new InputParser(new DefaultParser(), args);

            ApplicationConfig applicationConfig = new ApplicationConfigMaker(
                    new InputParser(new DefaultParser(), args).parseCommandLine(),
                    new GeneratorConfigValidator()
            ).getApplicationConfig();


            return new Application(
                    new RandomTransactionGenerator(
                            applicationConfig.transactionConfig,
                            new RandomPurchaseGenerator(
                                    applicationConfig.purchaseConfig,
                                    new RandomItemGenerator(
                                            getAvailableItems(applicationConfig),
                                            new Random()
                                    ),
                                    new Random()
                            ),
                            ThreadLocalRandom.current()
                    ),
                    applicationConfig.generatorConfig,
                    new TransactionFileWriter(
                            applicationConfig.generatorConfig.getOutDir(),
                            new CustomAdaptersGsonFactory().createGson()
                    )
            );
    }

    private static List<Item> getAvailableItems(ApplicationConfig applicationConfig) {
        InputStream in = Application.class.getClassLoader()
                .getResourceAsStream(applicationConfig.itemConfig.getItemsFilename());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        CSVItemParser parser = new CSVItemParser(bufferedReader);

        return parser.parse();
    }

    public static void runAndPrintErrors(String[] args) {
        try {
            Application app = getApplicationWithInjectedDependencies(args);
            app.run();
        } catch (RuntimeException e) {
            System.out.println("Error occured: " + e.getMessage());
        }
    }
}
