package generators.model;

import generators.NumberGenerator;
import generators.TimestampGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Item;
import model.Purchase;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ranges.Range;
import app.config.RangesDTO;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class TransactionGenerator {
    @NonNull
    private final PurchaseGenerator purchaseGenerator;
    @NonNull
    private final TimestampGenerator timestampGenerator;
    @NonNull
    private final NumberGenerator numberGenerator;

    public List<Transaction> generateTransactions(int count, RangesDTO ranges, List<Item> availableItems) {
        if (count <= 0) {
            return Collections.emptyList();
        }

        return IntStream.range(0, count)
                .mapToObj(id -> generateTransaction(id, ranges, availableItems))
                .collect(Collectors.toList());
    }

    private Transaction generateTransaction(int id, RangesDTO ranges, List<Item> availableItems) {
        ZonedDateTime generatedTimestamp = generateTimestamp(ranges);
        int generatedCustomerId = generateCustomerId(ranges.customerIdRange);
        List<Purchase> generatedPurchases = generatePurchasesList(ranges, availableItems);

        return new Transaction(id, generatedTimestamp, generatedCustomerId, generatedPurchases);
    }

    private ZonedDateTime generateTimestamp(RangesDTO ranges) {
        return timestampGenerator.generateTimestamp(ranges.timestampRange);
    }

    private int generateCustomerId(Range<Integer> customerIdRange) {
        return numberGenerator.nextInt(
                customerIdRange.getMinimum(),
                customerIdRange.getMaximum());
    }

    private List<Purchase> generatePurchasesList(RangesDTO ranges, List<Item> availableItems) {
        return purchaseGenerator.generatePurchases(
                generateCustomerId(ranges.purchasesCountRange),
                availableItems,
                ranges.quantityRange
        );
    }
}
