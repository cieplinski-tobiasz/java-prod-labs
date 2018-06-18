package serializers;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Purchase;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public final class TransactionDtoFactory {
    @NonNull
    private final DateTimeFormatter dateTimeFormatter;

    @Autowired
    public TransactionDtoFactory(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public TransactionDto from(@NonNull Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                dateTimeFormatter.format(transaction.getTimestamp()),
                transaction.getCustomerId(),
                Collections.unmodifiableList(transaction.getPurchases())
        );
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @JacksonXmlRootElement(localName = "transaction")
    public static final class TransactionDto {
        public final int id;
        public final String timestamp;
        public final int customer_id;
        public final List<Purchase> items;
    }
}
