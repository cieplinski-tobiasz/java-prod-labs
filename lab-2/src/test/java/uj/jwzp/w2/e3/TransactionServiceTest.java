package uj.jwzp.w2.e3;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class TransactionServiceTest {
    @Mock
    private PersistenceLayer persistenceLayer;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void shouldReturnFalseWhenTransactionNotSaved() {
        // given
        TransactionService uut = new TransactionService(persistenceLayer);
        Mockito.when(persistenceLayer.saveTransaction(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(Boolean.FALSE);
        Item item = new Item("name", BigDecimal.TEN);
        Customer customer = new Customer(1, "testcust", "testadr");

        // when
        boolean saved = uut.saveTransaction(customer.getId(), item.getName(), 1);

        // then
        Assertions.assertThat(saved).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenTransactionSaved() {
        // given
        TransactionService uut = new TransactionService(persistenceLayer);
        Mockito.when(persistenceLayer.saveTransaction(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(Boolean.TRUE);
        Item item = new Item("name", BigDecimal.TEN);
        Customer customer = new Customer(1, "testcust", "testadr");

        // when
        boolean saved = uut.saveTransaction(customer.getId(), item.getName(), 1);

        // then
        Assertions.assertThat(saved).isTrue();
    }


}