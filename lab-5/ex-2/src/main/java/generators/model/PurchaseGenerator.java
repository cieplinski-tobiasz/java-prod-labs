package generators.model;

import generators.NumberGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Item;
import model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ranges.Range;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public final class PurchaseGenerator {
    @NonNull
    private final ItemGenerator itemGenerator;
    @NonNull
    private final NumberGenerator numberGenerator;

    public List<Purchase> generatePurchases(int count, List<Item> availableItems, Range<Integer> quantityRange) {
        if (count <= 0) {
            log.warn("Purchases count is not a positive number.");
            return Collections.emptyList();
        }

        return IntStream.range(0, count)
                .mapToObj(i -> generatePurchase(availableItems, quantityRange))
                .collect(Collectors.toList());
    }

    private Purchase generatePurchase(List<Item> availableItems, Range<Integer> quantityRange) {
        return new Purchase(
                itemGenerator.generateItemFromList(availableItems),
                numberGenerator.nextInt(quantityRange.getMinimum(), quantityRange.getMaximum()));
    }
}
