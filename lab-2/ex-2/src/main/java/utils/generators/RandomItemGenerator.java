package utils.generators;

import model.Item;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomItemGenerator {
    private final List<Item> availableItems;
    private final Random random;

    public RandomItemGenerator(List<Item> availableItems, Random random) {
        Objects.requireNonNull(availableItems);
        Objects.requireNonNull(random);

        if (availableItems.isEmpty()) {
            throw new IllegalArgumentException("Items list must not be empty.");
        }

        this.availableItems = availableItems;
        this.random = random;
    }

    public Item generateItem() {
        return availableItems.get(random.nextInt(availableItems.size()));
    }
}
