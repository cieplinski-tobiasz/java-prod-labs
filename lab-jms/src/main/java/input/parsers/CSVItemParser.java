package input.parsers;

import utils.exceptions.ParseException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import model.Item;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public final class CSVItemParser implements Parser<Item> {
    private static final String SEPARATOR = ",";
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int COMPONENTS_AMOUNT = 2;

    @Override
    public Item parse(@NonNull String csv) {
        return getItemFromSplitLine(getSplitLine(csv));
    }

    private Item getItemFromSplitLine(String[] split) {
        String name = parseName(split[NAME_INDEX]);
        BigDecimal price = parsePrice(split[PRICE_INDEX]);

        return new Item(name, price);
    }

    private String parseName(String name) {
        validateNameFormat(name);

        return getNameWithoutQuotationMarks(name);
    }

    private void validateNameFormat(String name) {
        if (name.length() <= 2 || !name.startsWith("\"") || !name.endsWith("\"")) {
            ParseException pe = new ParseException(
                    "Item name could not be parsed. It must not be empty, and must be surrounded with quotation marks.");

            log.error("Could not parse item name: invalid format.", pe);
            throw pe;
        }
    }

    private String getNameWithoutQuotationMarks(String name) {
        return name.substring(1, name.length() - 1);
    }

    private BigDecimal parsePrice(String price) {
        try {
            return new BigDecimal(price);
        } catch (NumberFormatException e) {
            log.error("Could not parse item price: BigDecimal constructor threw an exception.", e);
            throw new ParseException("Item price could not be parsed. It must not be empty.");
        }
    }

    private String[] getSplitLine(String csv) {
        String[] split = csv.split(SEPARATOR);
        validateComponentsAmount(split);
        return split;
    }

    private void validateComponentsAmount(String[] split) {
        if (split.length != COMPONENTS_AMOUNT) {
            ParseException pe = new ParseException(
                    "CSV file has incorrect format. Every line should have 2 components separated by " + SEPARATOR
            );
            log.error(
                    "Could not parse csv file: invalid amount of components. Expected {}, but found {}.",
                    COMPONENTS_AMOUNT, split.length
            );
            throw pe;
        }
    }
}
