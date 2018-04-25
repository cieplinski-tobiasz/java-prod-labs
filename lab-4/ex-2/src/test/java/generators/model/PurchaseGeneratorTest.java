package generators.model;

import generators.NumberGenerator;
import model.Item;
import model.Purchase;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.ranges.Range;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

public class PurchaseGeneratorTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private NumberGenerator numberGenerator;
    @Mock
    private ItemGenerator itemGenerator;
    @Mock
    private Range<Integer> quantityRange;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenItemGeneratorIsNull() {
        Throwable thrown = catchThrowable(() -> new PurchaseGenerator(null, numberGenerator));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenNumberGeneratorIsNull() {
        Throwable thrown = catchThrowable(() -> new PurchaseGenerator(itemGenerator, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGeneratePurchaseReturnEmptyListWhenCountIsNegative() {
        PurchaseGenerator uut = new PurchaseGenerator(itemGenerator, numberGenerator);

        List<Purchase> result = uut.generatePurchases(-1, Collections.emptyList(), quantityRange);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGeneratePurchaseNotCallGeneratorsWhenCountIsNegative() {
        PurchaseGenerator uut = new PurchaseGenerator(itemGenerator, numberGenerator);

        uut.generatePurchases(-1, Collections.emptyList(), quantityRange);

        verifyZeroInteractions(itemGenerator, numberGenerator);
    }

    @Test
    public void shouldGeneratePurchaseGenerateEmptyListWhenCountIsZero() {
        PurchaseGenerator uut = new PurchaseGenerator(itemGenerator, numberGenerator);

        List<Purchase> result = uut.generatePurchases(0, Collections.emptyList(), quantityRange);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGeneratePurchaseNotCallGeneratorsWhenCountIsZero() {
        PurchaseGenerator uut = new PurchaseGenerator(itemGenerator, numberGenerator);

        uut.generatePurchases(0, Collections.emptyList(), quantityRange);

        verifyZeroInteractions(itemGenerator, numberGenerator);
    }

    @Test
    public void shouldGeneratePurchasesGenerateExactAmountOfPurchases() {
        int expectedAmount = 5;
        int expectedQuantity = 10;
        when(numberGenerator.nextInt(anyInt(), anyInt())).thenReturn(expectedQuantity);
        when(quantityRange.getMinimum()).thenReturn(4);
        when(quantityRange.getMaximum()).thenReturn(12);
        when(itemGenerator.generateItemFromList(any())).thenReturn(mock(Item.class));
        PurchaseGenerator uut = new PurchaseGenerator(itemGenerator, numberGenerator);

        List<Purchase> result = uut.generatePurchases(expectedAmount, Collections.emptyList(), quantityRange);

        assertThat(result).hasSize(expectedAmount).hasOnlyElementsOfType(Purchase.class);
        assertThat(result).extracting("quantity").containsOnly(expectedQuantity);
    }
}