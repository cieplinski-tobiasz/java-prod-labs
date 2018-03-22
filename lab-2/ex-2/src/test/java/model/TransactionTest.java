package model;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowNullPointerExceptionWhenZonedDateTimeIsNull() {
        Throwable thrown = catchThrowable(() -> new Transaction(0, null, 0, getMockedPurchases()));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPurchasesAreNull() {
        Throwable thrown = catchThrowable(() -> new Transaction(0, ZonedDateTime.now(), 0, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPurchasesListIsEmpty() {
        Throwable thrown = catchThrowable(() -> new Transaction(0, ZonedDateTime.now(), 0, Collections.emptyList()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldSumReturnPricesMultipliedByQuantities() {
        List<Purchase> purchases = getMockedPurchases();
        Transaction uut = new Transaction(0, ZonedDateTime.now(), 0, purchases);

        BigDecimal result = uut.getSum();

        assertThat(result).isEqualTo(BigDecimal.valueOf(100));
    }

    private List<Purchase> getMockedPurchases() {
        Item mockedItem = mock(Item.class);
        when(mockedItem.getPrice()).thenReturn(BigDecimal.TEN);
        Purchase mockedPurchase = mock(Purchase.class);
        when(mockedPurchase.getItem()).thenReturn(mockedItem);
        when(mockedPurchase.getQuantity()).thenReturn(2);

        return Collections.nCopies(5, mockedPurchase);
    }

}