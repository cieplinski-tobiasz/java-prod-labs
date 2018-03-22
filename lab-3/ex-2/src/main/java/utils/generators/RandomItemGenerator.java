package utils.generators;

import model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.cli.InputParser;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomItemGenerator {
    private static Logger logger = LoggerFactory.getLogger(RandomItemGenerator.class);

    private final List<Item> availableItems;
    private final Random random;

    public RandomItemGenerator(List<Item> availableItems, Random random) {
        Objects.requireNonNull(availableItems);
        Objects.requireNonNull(random);

        if (availableItems.isEmpty()) {
            logger.info("Given available items list is empty.");
            throw new IllegalArgumentException("Items list must not be empty.");
        }

        this.availableItems = availableItems;
        this.random = random;
    }

    public Item generateItem() {
        return availableItems.get(random.nextInt(availableItems.size()));
    }
}
