package utils.parsers;

import model.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CSVItemParser {
    private final String SEPARATOR = ",";
    private final BufferedReader bufferedReader;

    public CSVItemParser(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public List<Item> parse() {
        List<String> fileAsLines = getFileAsLines();
        checkIfThereAreAnyItems(fileAsLines);

        return fileAsLines.subList(1, fileAsLines.size()).stream()
                .map(this::parseCsvLine)
                .collect(Collectors.toList());
    }

    private List<String> getFileAsLines() {
        return getLinesOrThrowException();
    }

    private List<String> getLinesOrThrowException() {
        try {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (UncheckedIOException e) {
            throw new IllegalStateException("Could not open file.", e);
        }
    }

    private void checkIfThereAreAnyItems(List<String> items) {
        if (items.size() < 2) {
            throw new IllegalStateException("Csv file empty.");
        }
    }

    private Item parseCsvLine(String line) {
        String[] components = splitLinesIntoTwoComponents(line);

        return new Item(
                parseItemName(components[0]),
                parsePrice(components[1])
        );
    }

    private String[] splitLinesIntoTwoComponents(String line) {
        String[] components = line.split(SEPARATOR);
        checkIfThereAreExactlyTwoComponents(components);
        return components;
    }

    private void checkIfThereAreExactlyTwoComponents(String[] components) {
        if (components.length != 2) {
            throw new IllegalStateException("Csv file format incorrect.");
        }
    }

    private String parseItemName(String itemName) {
        checkIfItemNameHasValidFormat(itemName);
        return nameWithCutQuotationMarks(itemName);
    }

    private String nameWithCutQuotationMarks(String name) {
        return name.substring(1, name.length() - 1);
    }

    private void checkIfItemNameHasValidFormat(String itemName) {
        if (itemName.length() <= 2) {
            throw new IllegalStateException("Item name cannot be empty.");
        }
    }

    private BigDecimal parsePrice(String price) {
        try {
            return new BigDecimal(price);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Could not parse price.", e);
        }
    }
}
