package utils.generators;

import config.purchase.PurchaseConfig;
import model.Purchase;
import utils.ranges.Range;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPurchaseGenerator {
    private final PurchaseConfig purchaseConfig;
    private final RandomItemGenerator itemGenerator;
    private final Random random;

    public RandomPurchaseGenerator(PurchaseConfig purchaseConfig, RandomItemGenerator itemGenerator, Random random) {
        Objects.requireNonNull(purchaseConfig);
        Objects.requireNonNull(itemGenerator);
        Objects.requireNonNull(random);

        this.purchaseConfig = purchaseConfig;
        this.itemGenerator = itemGenerator;
        this.random = random;
    }

    public List<Purchase> generatePurchases(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Purchases count must be greater than zero.");
        }

        return IntStream.range(0, count)
                .mapToObj(i -> generatePurchase())
                .collect(Collectors.toList());
    }

    public Purchase generatePurchase() {
        return new Purchase(itemGenerator.generateItem(), generateQuantity());
    }

    private int generateQuantity() {
        Range<Integer> range = purchaseConfig.getQuantityRange();
        return random.nextInt(range.getMaximum()) + range.getMinimum();
    }
}
