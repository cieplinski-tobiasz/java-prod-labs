package utils.cli;


import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.List;

public class InputParser {
    private CommandLineParser commandLineParser;
    private String[] args;

    public InputParser(CommandLineParser commandLineParser, String[] args) {
        this.commandLineParser = commandLineParser;
        this.args = args;
    }

    public CommandLine parseCommandLine() {
        try {
            return commandLineParser.parse(getOptions(), args);
        } catch (ParseException e) {
            throw new IllegalStateException("Cannot parse command line.", e);
        }
    }

    public Options getOptions() {
        Options options = new Options();
        getOptionList().forEach(options::addOption);
        return options;
    }

    private List<Option> getOptionList() {
        return Arrays.asList(
                getCustomerIdRangeOption(),
                getDataRangeOption(),
                getItemsFileOption(),
                getItemsCountOption(),
                getItemsQuantityOption(),
                getEventsCountOption(),
                getOutputDirOption()
        );
    }

    private Option getCustomerIdRangeOption() {
        return Option.builder("customerIds")
                .numberOfArgs(2)
                .valueSeparator(':')
                .build();
    }

    private Option getDataRangeOption() {
        return Option.builder("dateRange")
                .numberOfArgs(2)
                .valueSeparator(':')
                .build();
    }

    private Option getItemsFileOption() {
        return Option.builder("itemsFile")
                .hasArg()
                .build();
    }

    private Option getItemsCountOption() {
        return Option.builder("itemsCount")
                .numberOfArgs(2)
                .valueSeparator(':')
                .build();
    }

    private Option getItemsQuantityOption() {
        return Option.builder("itemsQuantity")
                .numberOfArgs(2)
                .valueSeparator(':')
                .build();
    }

    private Option getEventsCountOption() {
        return Option.builder("eventsCount")
                .hasArg()
                .build();
    }

    private Option getOutputDirOption() {
        return Option.builder("outDir")
                .hasArg()
                .build();
    }
}
