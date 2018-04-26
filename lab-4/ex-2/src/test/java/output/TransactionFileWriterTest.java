package output;

import model.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import serializers.Serializer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionFileWriterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private Serializer<Transaction> serializer;

    @Mock
    private FilesWrapper files;

    @Mock
    private Transaction transaction;

    @Mock
    private Path path;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenSerializerIsNull() {
        Throwable thrown = catchThrowable(() -> new TransactionFileWriter(null, files));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenFilesWrapperIsNull() {
        Throwable thrown = catchThrowable(() -> new TransactionFileWriter(serializer, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldWriteTransactionsThrowNullPointerExceptionWhenTransactionsListIsNull() {
        TransactionFileWriter uut = new TransactionFileWriter(serializer, files);

        Throwable thrown = catchThrowable(() -> uut.writeTransactions(null, path));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldWriteTransactionsThrowNullPointerExceptionWhenPathIsNull() {
        TransactionFileWriter uut = new TransactionFileWriter(serializer, files);

        Throwable thrown = catchThrowable(() -> uut.writeTransactions(Collections.emptyList(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldWriteTransactionsCallCreateDirectoryIfDirectoryIsMissing() throws IOException {
        when(files.isDirectory(any())).thenReturn(true);
        when(files.notExists(any())).thenReturn(true);
        when(transaction.getId()).thenReturn(1);
        when(serializer.serialize(any())).thenReturn("serialized");
        when(path.resolve(any(String.class))).thenReturn(path);
        when(path.toAbsolutePath()).thenReturn(path);
        TransactionFileWriter uut = new TransactionFileWriter(serializer, files);

        uut.writeTransactions(Collections.singletonList(transaction), path);

        verify(files, times((1))).createDirectory(path);
    }

    @Test
    public void shouldWriteTransactionsThrowUncheckedIOExceptionWhenDirectoryCannotBeCreated() throws IOException {
        doThrow(IOException.class).when(files).createDirectory(any());
        when(files.isDirectory(any())).thenReturn(true);
        when(files.notExists(any())).thenReturn(true);
        when(transaction.getId()).thenReturn(1);
        when(serializer.serialize(any())).thenReturn("serialized");
        TransactionFileWriter uut = new TransactionFileWriter(serializer, files);

        Throwable thrown = catchThrowable(() -> uut.writeTransactions(Collections.singletonList(transaction), path));

        assertThat(thrown).isInstanceOf(UncheckedIOException.class);
    }

    @Test
    public void shouldWriteTransactionsCallWriteFile() throws IOException {
        String serialized = "serialized";
        when(files.isDirectory(any())).thenReturn(true);
        when(transaction.getId()).thenReturn(1);
        when(serializer.serialize(any())).thenReturn(serialized);
        when(path.resolve(any(String.class))).thenReturn(path);
        when(path.toAbsolutePath()).thenReturn(path);
        TransactionFileWriter uut = new TransactionFileWriter(serializer, files);

        uut.writeTransactions(Collections.singletonList(transaction), path);

        verify(files, times((1))).write(path, serialized.getBytes());
    }

    @Test
    public void shouldWriteTransactionsThrowUncheckedIOExceptionWhenFileWriteFails() throws IOException {
        doThrow(IOException.class).when(files).write(any(), any());
        when(files.isDirectory(any())).thenReturn(true);
        when(transaction.getId()).thenReturn(1);
        when(serializer.serialize(any())).thenReturn("serialized");
        when(path.resolve(any(String.class))).thenReturn(path);
        when(path.toAbsolutePath()).thenReturn(path);
        TransactionFileWriter uut = new TransactionFileWriter(serializer, files);

        Throwable thrown = catchThrowable(() -> uut.writeTransactions(Collections.singletonList(transaction), path));

        assertThat(thrown).isInstanceOf(UncheckedIOException.class);
    }

}