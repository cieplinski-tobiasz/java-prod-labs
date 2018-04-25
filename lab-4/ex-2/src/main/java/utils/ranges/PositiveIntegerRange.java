package utils.ranges;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositiveIntegerRange implements Range<Integer> {
    private final Integer minimum;
    private final Integer maximum;

    public static PositiveIntegerRange between(int minimum, int maximum) {
        if (minimum <= 0 || maximum <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("Components of range must be positive.");
            log.warn("Negative integer range.", iae);
            throw iae;
        }

        if (minimum > maximum) {
            IllegalArgumentException iae = new IllegalArgumentException(
                    "\'From\' number must be less than or equal to \'To\' number."
            );
            log.warn("Wrong integer range.", iae);
            throw iae;
        }

        return new PositiveIntegerRange(minimum, maximum);
    }
}

