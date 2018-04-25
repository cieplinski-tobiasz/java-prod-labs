package serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionSerializerTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private TransactionDtoFactory dtoFactory;
    @Mock
    private TransactionDtoFactory.TransactionDto transactionDto;
    @Mock
    private Transaction transaction;

    @Test
    public void shouldThrowNullPointerExceptionWhenObjectMapperIsNull() {
        Throwable result = catchThrowable(() -> new TransactionSerializer(null, dtoFactory));

        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenDtoFactoryIsNull() {
        Throwable result = catchThrowable(() -> new TransactionSerializer(objectMapper, null));

        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowUncheckedIOExceptionWhenSerializationFails() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        when(dtoFactory.from(any(Transaction.class))).thenReturn(transactionDto);
        TransactionSerializer uut = new TransactionSerializer(objectMapper, dtoFactory);

        Throwable result = catchThrowable(() -> uut.serialize(transaction));

        assertThat(result).isInstanceOf(UncheckedIOException.class);
    }

    @Test
    public void shouldSerializeSuccessfullyWithValidArguments() throws JsonProcessingException {
        String expected = "{message: \"OK\"}";
        when(objectMapper.writeValueAsString(any())).thenReturn(expected);
        when(dtoFactory.from(any(Transaction.class))).thenReturn(transactionDto);
        TransactionSerializer uut = new TransactionSerializer(objectMapper, dtoFactory);

        String result = uut.serialize(transaction);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldCallObjectMapperWhenSerializing() throws JsonProcessingException {
        String expected = "{message: \"OK\"}";
        when(objectMapper.writeValueAsString(any())).thenReturn(expected);
        when(dtoFactory.from(any(Transaction.class))).thenReturn(transactionDto);
        TransactionSerializer uut = new TransactionSerializer(objectMapper, dtoFactory);

        String result = uut.serialize(transaction);

        verify(objectMapper, times(1)).writeValueAsString(any());
    }

    @Test
    public void shouldCallDtoFactoryWhenSerializing() throws JsonProcessingException {
        String expected = "{message: \"OK\"}";
        when(objectMapper.writeValueAsString(any())).thenReturn(expected);
        when(dtoFactory.from(any(Transaction.class))).thenReturn(transactionDto);
        TransactionSerializer uut = new TransactionSerializer(objectMapper, dtoFactory);

        String result = uut.serialize(transaction);

        verify(dtoFactory, times(1)).from(any(Transaction.class));
    }
}