package generators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ranges.Range;

import java.time.Instant;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public final class TimestampGenerator {
    @NonNull
    private final NumberGenerator numberGenerator;

    public ZonedDateTime generateTimestamp(@NonNull Range<ZonedDateTime> dateTimeRange) {
        ZonedDateTime from = dateTimeRange.getMinimum();
        ZonedDateTime to = dateTimeRange.getMaximum();

        long generatedTimestamp = numberGenerator.nextLong(from.toEpochSecond(), to.toEpochSecond());

        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(generatedTimestamp), from.getZone());
    }
}
