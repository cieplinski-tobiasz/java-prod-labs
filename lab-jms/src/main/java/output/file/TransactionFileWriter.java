package output.file;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import serializers.Serializer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

@Component
@Slf4j
public final class TransactionFileWriter {
    @NonNull
    private final Serializer<Transaction> serializer;
    @NonNull
    private final FilesWrapper files;

    @Autowired
    public TransactionFileWriter(Serializer<Transaction> serializer, FilesWrapper files) {
        this.serializer = serializer;
        this.files = files;
    }

    public void writeTransactions(@NonNull List<Transaction> transactions, @NonNull Path path) {
        Path absolute = path.toAbsolutePath();

        if (isDirectoryMissing(absolute)) {
            createDirectory(absolute);
        }

        transactions.forEach(transaction -> writeTransaction(transaction, absolute));
    }

    private boolean isDirectoryMissing(Path path) {
        return files.notExists(path);
    }

    private void createDirectory(Path path) {
        try {
            files.createDirectory(path);
        } catch (IOException e) {
            log.error("Could not create directory. " +
                    "Maybe given path is not a directory, or parent directory does not exist?", e);
            throw new UncheckedIOException(e);
        }
    }

    private void writeTransaction(Transaction transaction, Path path) {
        String filename = "transaction-" + transaction.getId();
        String serialized = serializer.serialize(transaction);
        writeFile(path, filename, serialized);
    }

    private void writeFile(Path path, String filename, String serialized) {
        try {
            files.write(path.resolve(filename).toAbsolutePath(), serialized.getBytes());
        } catch (IOException e) {
            log.info("File write failed.", e);
            throw new UncheckedIOException(e);
        }
    }
}
