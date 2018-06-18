package generators.model;

import app.config.RangesDTO;
import generators.NumberGenerator;
import generators.TimestampGenerator;
import model.Item;
import model.Purchase;
import model.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.ranges.DateTimeRange;
import utils.ranges.PositiveIntegerRange;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

public class TransactionGeneratorTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private PurchaseGenerator purchaseGenerator;

    @Mock
    private TimestampGenerator timestampGenerator;

    @Mock
    private NumberGenerator numberGenerator;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenPurchaseGeneratorIsNull() {
        Throwable thrown = catchThrowable(
                () -> new TransactionGenerator(null, timestampGenerator, numberGenerator));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenTimestampGeneratorIsNull() {
        Throwable thrown = catchThrowable(
                () -> new TransactionGenerator(purchaseGenerator, null, numberGenerator));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenNumberGeneratorIsNull() {
        Throwable thrown = catchThrowable(
                () -> new TransactionGenerator(purchaseGenerator, timestampGenerator, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGenerateTransactionGenerateEmptyListWhenCountIsNegative() {
        RangesDTO rangesDtoMock = mock(RangesDTO.class);
        TransactionGenerator uut = new TransactionGenerator(purchaseGenerator, timestampGenerator, numberGenerator);

        List<Transaction> result = uut.generateTransactions(-1, rangesDtoMock, Collections.emptyList());

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGeneratePurchaseNotCallGeneratorsWhenCountIsNegative() {
        RangesDTO rangesDtoMock = mock(RangesDTO.class);
        TransactionGenerator uut = new TransactionGenerator(purchaseGenerator, timestampGenerator, numberGenerator);

        uut.generateTransactions(-1, rangesDtoMock, Collections.emptyList());

        verifyZeroInteractions(purchaseGenerator, timestampGenerator, numberGenerator);
    }

    @Test
    public void shouldGenerateTransactionGenerateEmptyListWhenCountIsZero() {
        RangesDTO rangesDtoMock = mock(RangesDTO.class);
        TransactionGenerator uut = new TransactionGenerator(purchaseGenerator, timestampGenerator, numberGenerator);

        List<Transaction> result = uut.generateTransactions(0, rangesDtoMock, Collections.emptyList());

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGeneratePurchaseNotCallGeneratorsWhenCountIsZero() {
        RangesDTO rangesDtoMock = mock(RangesDTO.class);
        TransactionGenerator uut = new TransactionGenerator(purchaseGenerator, timestampGenerator, numberGenerator);

        uut.generateTransactions(0, rangesDtoMock, Collections.emptyList());

        verifyZeroInteractions(purchaseGenerator, timestampGenerator, numberGenerator);
    }

    @Test
    public void shouldGenerateTransactionsGenerateExactAmountOfTransactions() {
        ZonedDateTime timestamp = ZonedDateTime.now();
        int customerId = 1;
        int count = 5;
        List<Item> items = Collections.singletonList(new Item("przedmiot", BigDecimal.ONE));
        List<Purchase> purchases = Collections.singletonList(
                new Purchase(items.get(0), 1)
        );
        when(timestampGenerator.generateTimestamp(any())).thenReturn(timestamp);
        when(numberGenerator.nextInt(anyInt(), anyInt())).thenReturn(customerId);
        when(purchaseGenerator.generatePurchases(anyInt(), any(), any())).thenReturn(purchases);
        RangesDTO rangesDTO = RangesDTO.builder()
                .customerIdRange(mock(PositiveIntegerRange.class)).purchasesCountRange(mock(PositiveIntegerRange.class))
                .quantityRange(mock(PositiveIntegerRange.class)).timestampRange(mock(DateTimeRange.class))
                .build();

        TransactionGenerator uut = new TransactionGenerator(purchaseGenerator, timestampGenerator, numberGenerator);

        List<Transaction> result = uut.generateTransactions(count, rangesDTO, items);

        assertThat(result).hasSize(count).hasOnlyElementsOfType(Transaction.class);
        assertThat(result).extracting("timestamp").containsOnly(timestamp);
        assertThat(result).extracting("customerId").containsOnly(customerId);
        assertThat(result).extracting("purchases").containsOnly(purchases);
    }
}