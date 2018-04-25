package input.cli;

import app.config.GeneratorConfig;
import app.config.RangesDTO;
import input.parsers.Parser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import utils.ranges.DateTimeRange;
import utils.ranges.Range;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class GeneratorConfigSupplier implements Supplier<GeneratorConfig> {
    @NonNull
    private final Environment env;
    @NonNull
    private final Parser<Range<Integer>> intRangeParser;
    @NonNull
    private final Parser<Range<ZonedDateTime>> dateTimeParser;
    @NonNull
    private final Parser<Integer> intParser;
    @NonNull
    private final Parser<Path> pathParser;

    @Override
    public GeneratorConfig get() {
        RangesDTO ranges = RangesDTO.builder()
                .purchasesCountRange(getItemsCount())
                .customerIdRange(getCustomerIds())
                .quantityRange(getItemsQuantity())
                .timestampRange(getDateRange())
                .build();

        return new GeneratorConfig(ranges, getItemsFile(), getEventsCount(), getOutDir());
    }

    private Path getOutDir() {
        return pathParser.parse(env.getProperty("outDir", "."));
    }

    private Range<Integer> getCustomerIds() {
        return intRangeParser.parse(env.getProperty("customerIds", "1:20"));
    }

    private Range<Integer> getItemsCount() {
        return intRangeParser.parse(env.getProperty("itemsCount", "1:5"));
    }

    private Range<Integer> getItemsQuantity() {
        return intRangeParser.parse(env.getProperty("itemsQuantity", "1:5"));
    }

    private String getItemsFile() {
        return env.getProperty("itemsFile", "items.csv");
    }

    private int getEventsCount() {
        return intParser.parse(env.getProperty("eventsCount", "100"));
    }

    private Range<ZonedDateTime> getDateRange() {
        return env.containsProperty("dateRange")
                ? dateTimeParser.parse(env.getProperty("dateRange"))
                : getTodayRange();
    }

    private Range<ZonedDateTime> getTodayRange() {
        ZonedDateTime today = ZonedDateTime.now();

        return DateTimeRange.between(
                today.with(LocalTime.MIN),
                today.with(LocalTime.MAX)
        );
    }
}
