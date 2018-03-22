package utils.generators;

import config.purchase.PurchaseConfig;
import model.Item;
import model.Purchase;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.ranges.Range;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class RandomPurchaseGeneratorTest {
    @Mock
    private Range<Integer> quantityRange;

    @Mock
    private PurchaseConfig purchaseConfig;

    @Mock
    private RandomItemGenerator randomItemGenerator;

    @Mock
    private Random random;

    @Mock
    private Item item;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowNullPointerExceptionWhenPurchaseConfigIsNull() {
        Throwable thrown = catchThrowable(() -> new RandomPurchaseGenerator(null, randomItemGenerator, random));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenRandomItemGeneratorIsNull() {
        Throwable thrown = catchThrowable(() -> new RandomPurchaseGenerator(purchaseConfig, null, random));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenRandomIsNull() {
        Throwable thrown = catchThrowable(() -> new RandomPurchaseGenerator(purchaseConfig, randomItemGenerator, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGeneratePurchaseWorkAccordingToConfig() {
        when(randomItemGenerator.generateItem()).thenReturn(item);
        when(quantityRange.getMinimum()).thenReturn(1);
        when(quantityRange.getMaximum()).thenReturn(2);
        when(purchaseConfig.getQuantityRange()).thenReturn(quantityRange);
        when(random.nextInt(anyInt())).thenAnswer(i -> ((Integer) i.getArgument(0)) - 1);
        RandomPurchaseGenerator uut = new RandomPurchaseGenerator(purchaseConfig, randomItemGenerator, random);

        Purchase result = uut.generatePurchase();

        assertThat(result.getQuantity()).isBetween(1, 2);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenCountIsNegative() {
        RandomPurchaseGenerator uut = new RandomPurchaseGenerator(purchaseConfig, randomItemGenerator, random);

        Throwable thrown = catchThrowable(() -> uut.generatePurchases(-2));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenCountIsZero() {
        RandomPurchaseGenerator uut = new RandomPurchaseGenerator(purchaseConfig, randomItemGenerator, random);

        Throwable thrown = catchThrowable(() -> uut.generatePurchases(0));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldGenerateExactAmountOfPurchases() {
        when(randomItemGenerator.generateItem()).thenReturn(item);
        when(quantityRange.getMinimum()).thenReturn(1);
        when(quantityRange.getMaximum()).thenReturn(2);
        when(purchaseConfig.getQuantityRange()).thenReturn(quantityRange);
        when(random.nextInt(anyInt())).thenAnswer(i -> ((Integer) i.getArgument(0)) - 1);
        RandomPurchaseGenerator uut = new RandomPurchaseGenerator(purchaseConfig, randomItemGenerator, random);

        List<Purchase> result = uut.generatePurchases(3);

        assertThat(result).hasSize(3).hasOnlyElementsOfType(Purchase.class);
        result.forEach(element -> assertThat(element.getQuantity()).isBetween(1, 2));
    }
}