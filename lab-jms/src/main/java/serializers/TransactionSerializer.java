package serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UncheckedIOException;

@Component
@Slf4j
public final class TransactionSerializer implements Serializer<Transaction> {
    @NonNull
    private final ObjectMapper mapper;
    @NonNull
    private final TransactionDtoFactory dtoFactory;

    @Autowired
    public TransactionSerializer(ObjectMapper mapper, TransactionDtoFactory dtoFactory) {
        this.mapper = mapper;
        this.dtoFactory = dtoFactory;
    }

    @Override
    public String serialize(Transaction obj) {
        TransactionDtoFactory.TransactionDto dto = dtoFactory.from(obj);
        return getSerializedObjectOrThrowException(dto);
    }

    private String getSerializedObjectOrThrowException(TransactionDtoFactory.TransactionDto dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize transaction.", e);
            throw new UncheckedIOException(e);
        }
    }
}
