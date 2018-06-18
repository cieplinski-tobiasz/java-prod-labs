package app;

import app.config.GeneratorConfig;
import generators.model.TransactionGenerator;
import lombok.NonNull;
import model.Item;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import output.file.TransactionFileWriter;
import output.jms.JmsSender;

import java.util.List;
import java.util.function.Supplier;

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
    @NonNull
    private final JmsSender jmsSender;

    public Generator(TransactionGenerator generator, TransactionFileWriter fileWriter,
                     Supplier<List<Item>> itemsSupplier, Supplier<GeneratorConfig> configSupplier,
                     JmsSender jmsSender) {
        this.generator = generator;
        this.fileWriter = fileWriter;
        this.itemsSupplier = itemsSupplier;
        this.configSupplier = configSupplier;
        this.jmsSender = jmsSender;
    }

    public void run() {
        GeneratorConfig config = configSupplier.get();
        List<Transaction> transactions = generator.generateTransactions(
                config.getEventsCount(),
                config.getRanges(),
                itemsSupplier.get()
        );
        jmsSender.sendTransactions(transactions);
        fileWriter.writeTransactions(transactions, config.getOutputDirectory());
    }
}
