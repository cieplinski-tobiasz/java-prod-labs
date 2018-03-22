package utils.ranges;

public class PositiveIntegerRange implements Range<Integer> {
    private final Integer from;
    private final Integer to;

    private PositiveIntegerRange(int fromInclusive, int toExclusive) {
        this.from = fromInclusive;
        this.to = toExclusive;
    }

    public static PositiveIntegerRange between(int fromInclusive, int toExclusive) {
        if (fromInclusive <= 0 || toExclusive <= 0) {
            throw new IllegalArgumentException("Components of range must be positive.");
        }

        if (fromInclusive >= toExclusive) {
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
