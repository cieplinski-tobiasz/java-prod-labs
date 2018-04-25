package app;

import app.config.GeneratorConfig;
import generators.model.TransactionGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Item;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import output.TransactionFileWriter;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public final class Generator {
    @NonNull
    private final TransactionGenerator generator;
    @NonNull
    private final TransactionFileWriter fileWriter;
    @NonNull
    private final Supplier<List<Item>> itemsSupplier;
    @NonNull
    private final Supplier<GeneratorConfig> configSupplier;

    public void run() {
        GeneratorConfig config = configSupplier.get();
        List<Transaction> transactions = generator.generateTransactions(
                config.getEventsCount(),
                config.getRanges(),
                itemsSupplier.get()
        );
        fileWriter.writeTransactions(transactions, config.getOutputDirectory());
    }
}
