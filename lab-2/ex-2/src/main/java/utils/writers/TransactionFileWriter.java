package utils.writers;

import com.google.gson.Gson;
import model.Transaction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class TransactionFileWriter {
    private Path path;
    private Gson gson;

    public TransactionFileWriter(Path path, Gson gson) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(gson);

        this.path = path;
        this.gson = gson;
    }

    public void writeTransactions(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Cannot write empty transactions.");
        }

        transactions.forEach(this::writeTransaction);
    }

    private void writeTransaction(Transaction transaction) {
        String filename = "transaction-" + transaction.getId();
        String json = gson.toJson(transaction);
        writeOrThrowException(filename, json);
    }

    private void writeOrThrowException(String filename, String json) {
        try {
            Files.write(path.resolve(filename).toAbsolutePath(), json.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("Could not write the file", e);
        }
    }
}
