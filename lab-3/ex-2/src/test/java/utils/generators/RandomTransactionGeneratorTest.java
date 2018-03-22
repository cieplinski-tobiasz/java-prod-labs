package utils.generators;

import config.transaction.TransactionConfig;
import model.Purchase;
import model.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.ranges.DateTimeRange;
import utils.ranges.Range;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class RandomTransactionGeneratorTest {
    @Mock
    private TransactionConfig transactionConfig;

    @Mock
    private Range<Integer> range;

    @Mock
    private RandomPurchaseGenerator purchaseGenerator;

    @Mock
    private Purchase purchase;

    @Mock
    private ThreadLocalRandom random;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowNullPointerExceptionWhenTransactionConfigIsNull() {
        Throwable thrown = catchThrowable(() -> new RandomTransactionGenerator(null, purchaseGenerator, random));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPurchaseGeneratorIsNull() {
        Throwable thrown = catchThrowable(() -> new RandomTransactionGenerator(transactionConfig, null, random));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenRandomIsNull() {
        Throwable thrown = catchThrowable(() -> new RandomTransactionGenerator(transactionConfig, purchaseGenerator, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGenerateTransactionWorkAccordingToConfig() {
        initializeValidMocks();
        ZonedDateTime from = ZonedDateTime.of(1999, 10, 10, 10, 10, 10, 0, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2000, 10, 10, 10, 10, 10, 10, ZoneId.of("Z"));
        when(transactionConfig.getDateTimeRange()).thenReturn(DateTimeRange.between(from, to));
        RandomTransactionGenerator uut = new RandomTransactionGenerator(transactionConfig, purchaseGenerator, random);
        int id = 0;

        Transaction result = uut.generateTransaction(id);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getZonedDateTime()).isEqualTo(from);
        assertThat(result.getCustomerId()).isEqualTo(2);
        assertThat(result.getPurchases()).hasSize(2);

    }

    @Test
    public void shouldGenerateExactAmontOfTransactions() {
        initializeValidMocks();
        ZonedDateTime from = ZonedDateTime.of(1999, 10, 10, 10, 10, 10, 0, ZoneId.of("Z"));
        ZonedDateTime to = ZonedDateTime.of(2000, 10, 10, 10, 10, 10, 10, ZoneId.of("Z"));
        when(transactionConfig.getDateTimeRange()).thenReturn(DateTimeRange.between(from, to));
        RandomTransactionGenerator uut = new RandomTransactionGenerator(transactionConfig, purchaseGenerator, random);

        List<Transaction> result = uut.generateTransactions(3);

        assertThat(result).hasSize(3).hasOnlyElementsOfType(Transaction.class);
        result.forEach(element -> {
            assertThat(element.getPurchases().size()).isBetween(1, 3);
            assertThat(element.getCustomerId()).isBetween(1, 3);
            assertThat(element.getZonedDateTime()).isBefore(to);
            assertThat(element.getZonedDateTime()).isAfterOrEqualTo(from);
        });
    }

    private void initializeValidMocks() {
        when(purchaseGenerator.generatePurchases(anyInt())).thenReturn(getMockedPurchases(2));
        when(range.getMinimum()).thenReturn(1);
        when(range.getMaximum()).thenReturn(3);
        when(transactionConfig.getCustomerIdRange()).thenReturn(range);
        when(transactionConfig.getPurchasesCountRange()).thenReturn(range);
        when(random.nextInt(anyInt(), anyInt())).thenAnswer(i -> ((Integer) i.getArgument(1)) - 1);
        when(random.nextLong(anyLong(), anyLong())).thenAnswer(i -> i.getArgument(0));
    }

    private List<Purchase> getMockedPurchases(int count) {
        return Collections.nCopies(count, purchase);
    }

}