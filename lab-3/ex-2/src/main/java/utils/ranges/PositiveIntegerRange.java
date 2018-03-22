package utils.ranges;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.cli.InputParser;

public class PositiveIntegerRange implements Range<Integer> {
    private static Logger logger = LoggerFactory.getLogger(PositiveIntegerRange.class);

    private final Integer from;
    private final Integer to;

    private PositiveIntegerRange(int fromInclusive, int toExclusive) {
        this.from = fromInclusive;
        this.to = toExclusive;
    }

    public static PositiveIntegerRange between(int fromInclusive, int toExclusive) {
        if (fromInclusive <= 0 || toExclusive <= 0) {
            logger.info("Negative integer range.");
            throw new IllegalArgumentException("Components of range must be positive.");
        }

        if (fromInclusive >= toExclusive) {
            logger.info("Wrong integer range.");
            throw new IllegalArgumentException("\'From\' number must be smaller than \'To\' number.");
        }

        return new PositiveIntegerRange(fromInclusive, toExclusive);
    }

    public Integer getMinimum() {
        return from;
    }

    public Integer getMaximum() {
        return to;
    }

    public boolean in(Integer e) {
        return e >= from && e < to;
    }
}
