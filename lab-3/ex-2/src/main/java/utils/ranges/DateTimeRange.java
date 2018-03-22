package utils.ranges;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.cli.InputParser;

import java.time.ZonedDateTime;
import java.util.Objects;

public class DateTimeRange implements Range<ZonedDateTime> {
    private static Logger logger = LoggerFactory.getLogger(DateTimeRange.class);

    private final ZonedDateTime from;
    private final ZonedDateTime to;

    private DateTimeRange(ZonedDateTime from, ZonedDateTime to) {
        this.from = from;
        this.to = to;
    }

    public static DateTimeRange between(ZonedDateTime fromInclusive, ZonedDateTime toExclusive) {
        Objects.requireNonNull(fromInclusive);
        Objects.requireNonNull(toExclusive);

        if (toExclusive.isBefore(fromInclusive) || toExclusive.isEqual(fromInclusive)) {
            logger.info("Invalid date range.");
            throw new IllegalArgumentException("\'From\' date must be before \'To\' date.");
        }

        return new DateTimeRange(fromInclusive, toExclusive);
    }

    @Override
    public ZonedDateTime getMinimum() {
        return from;
    }

    @Override
    public ZonedDateTime getMaximum() {
        return to;
    }

    @Override
    public boolean in(ZonedDateTime e) {
        return (e.isAfter(from) || e.isEqual(from)) && e.isBefore(to);
    }
}
