package utils.generators;

import config.transaction.TransactionConfig;
import model.Transaction;
import utils.ranges.Range;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomTransactionGenerator {
    private final TransactionConfig transactionConfig;
    private final RandomPurchaseGenerator purchaseGenerator;
    private final ThreadLocalRandom random;

    public RandomTransactionGenerator(TransactionConfig transactionConfig, RandomPurchaseGenerator purchaseGenerator, ThreadLocalRandom random) {
        Objects.requireNonNull(transactionConfig);
        Objects.requireNonNull(purchaseGenerator);
        Objects.requireNonNull(random);

        this.transactionConfig = transactionConfig;
        this.purchaseGenerator = purchaseGenerator;
        this.random = random;
    }

    public List<Transaction> generateTransactions(int count) {
        return IntStream.range(0, count)
                .mapToObj(this::generateTransaction)
                .collect(Collectors.toList());
    }

    public Transaction generateTransaction(int id) {
        return new Transaction(
                id,
                generateTimestamp(),
                generateCustomerId(),
                purchaseGenerator.generatePurchases(generatePurchasesCount())
        );
    }


    private ZonedDateTime generateTimestamp() {
        Range<ZonedDateTime> range = transactionConfig.getDateTimeRange();
        ZoneId zoneId = range.getMinimum().getZone();

        long generatedTimestamp = random.nextLong(range.getMinimum().toEpochSecond(), range.getMaximum().toEpochSecond());

        Instant instant = Instant.ofEpochSecond(generatedTimestamp);

        return ZonedDateTime.ofInstant(instant, zoneId);
    }

    private int generateCustomerId() {
        Range<Integer> range = transactionConfig.getCustomerIdRange();

        return random.nextInt(range.getMinimum(), range.getMaximum());
    }

    private int generatePurchasesCount() {
        Range<Integer> range = transactionConfig.getPurchasesCountRange();

        return random.nextInt(range.getMinimum(), range.getMaximum());
    }
}
