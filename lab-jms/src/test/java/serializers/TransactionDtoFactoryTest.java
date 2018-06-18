package serializers;

import model.Purchase;
import model.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransactionDtoFactoryTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Transaction transaction;

    @Mock
    private DateTimeFormatter dateTimeFormatter;

    @Mock
    private ZonedDateTime zonedDateTime;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenDateTimeFormatterIsNull() {
        Throwable thrown = catchThrowable(() -> new TransactionDtoFactory(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldFromThrowNullPointerExceptionWhenTransactionIsNull() {
        TransactionDtoFactory uut = new TransactionDtoFactory(dateTimeFormatter);

        Throwable thrown = catchThrowable(() -> uut.from(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldFromReturnValidTransactionDtoInstance() {
        when(transaction.getId()).thenReturn(1);
        when(transaction.getTimestamp()).thenReturn(zonedDateTime);
        when(transaction.getCustomerId()).thenReturn(1);
        when(transaction.getPurchases()).thenReturn(Collections.emptyList());
        when(dateTimeFormatter.format(any())).thenReturn("formatted");
        TransactionDtoFactory uut = new TransactionDtoFactory(dateTimeFormatter);

        TransactionDtoFactory.TransactionDto dto = uut.from(transaction);

        assertThat(dto).hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("timestamp", "formatted")
                .hasFieldOrPropertyWithValue("customer_id", 1);

        assertThat(dto.items).isEmpty();
    }
}