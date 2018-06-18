package utils.ranges;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeRange implements Range<ZonedDateTime> {
    private final ZonedDateTime minimum;
    private final ZonedDateTime maximum;

    public static DateTimeRange between(@NonNull ZonedDateTime minimum, @NonNull ZonedDateTime maximum) {
        if (maximum.isBefore(minimum)) {
            IllegalArgumentException iae = new IllegalArgumentException("\'From\' date must be before \'To\' date.");
            log.warn("Invalid date range.", iae);
            throw iae;
        }

        return new DateTimeRange(minimum, maximum);
    }
}
