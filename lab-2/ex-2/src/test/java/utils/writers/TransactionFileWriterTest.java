package utils.writers;

import com.google.gson.Gson;
import json.gson.factories.CustomAdaptersGsonFactory;
import model.Item;
import model.Purchase;
import model.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class TransactionFileWriterTest {
    private Gson gson = new CustomAdaptersGsonFactory().createGson();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void shouldThrowNullPointerExceptionWhenPathIsNull() {
        Throwable thrown = catchThrowable(() -> new TransactionFileWriter(null, gson));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenGsonIsNull() {
        Throwable thrown = catchThrowable(() -> new TransactionFileWriter(Paths.get(""), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenTransactionListIsNull() {
        TransactionFileWriter uut = new TransactionFileWriter(Paths.get(""), gson);

        Throwable thrown = catchThrowable(() -> uut.writeTransactions(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTransactionListIsEmpty() {
        TransactionFileWriter uut = new TransactionFileWriter(Paths.get(""), gson);

        Throwable thrown = catchThrowable(() -> uut.writeTransactions(Collections.emptyList()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenFileAlreadyPresent() throws IOException {
        Path path = folder.newFile("transaction-0").toPath();
        Transaction transaction = new Transaction(0,
                ZonedDateTime.now(),
                0,
                Collections.singletonList(new Purchase(new Item("test", BigDecimal.ONE), 2)));
        TransactionFileWriter uut = new TransactionFileWriter(path, gson);

        Throwable thrown = catchThrowable(() -> uut.writeTransactions(Collections.singletonList(transaction)));

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasMessage("Could not write the file");
    }

    @Test
    public void shouldWriteFileWhenNoErrors() throws IOException {
        File directory = folder.newFolder();
        Transaction transaction = new Transaction(0,
                ZonedDateTime.now(),
                0,
                Collections.singletonList(new Purchase(new Item("test", BigDecimal.ONE), 2)));
        TransactionFileWriter uut = new TransactionFileWriter(directory.toPath(), gson);

        uut.writeTransactions(Collections.singletonList(transaction));

        assertThat(directory.list()).contains("transaction-0");
    }

}