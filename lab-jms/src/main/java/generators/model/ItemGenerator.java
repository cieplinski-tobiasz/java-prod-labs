package generators.model;

import generators.NumberGenerator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public final class ItemGenerator {
    @NonNull
    private final NumberGenerator numberGenerator;

    @Autowired
    public ItemGenerator(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public Item generateItemFromList(List<Item> availableItems) {
        validateItemsList(availableItems);

        return availableItems.get(getRandomIndex(availableItems));
    }

    private void validateItemsList(List<Item> availableItems) {
        if (availableItems.isEmpty()) {
            IllegalArgumentException ire = new IllegalArgumentException("Given available items list is empty.");
            log.warn("Item list is empty. Please check if CSV file is properly formatted and has data.", ire);
            throw ire;
        }
    }

    private int getRandomIndex(List<Item> items) {
        return numberGenerator.nextInt(items.size() - 1);
    }
}
