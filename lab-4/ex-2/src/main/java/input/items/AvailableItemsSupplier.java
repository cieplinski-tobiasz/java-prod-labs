package input.items;

import input.parsers.CSVItemParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public final class AvailableItemsSupplier implements Supplier<List<Item>> {
    private static final int HEADER_LINES_AMOUNT = 1;

    @NonNull
    private final ResourceReader resourceReader;
    @NonNull
    private final CSVItemParser itemParser;
    @NonNull
    private final Environment env;

    @Override
    public List<Item> get() {
        String filename = env.getProperty("itemsFile", "items.csv");

        return resourceReader.getResourceLines(filename)
                .skip(HEADER_LINES_AMOUNT)
                .map(itemParser::parse)
                .collect(Collectors.toList());
    }
}
