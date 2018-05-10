package input.parsers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.exceptions.ParseException;
import utils.ranges.DateTimeRange;
import utils.ranges.Range;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public final class ZonedDateTimeParser implements Parser<Range<ZonedDateTime>> {
    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 1;

    @NonNull
    private final DateTimeFormatter dateTimeFormatter;

    @Override
    public Range<ZonedDateTime> parse(@NonNull String value) {
        validateRangeSyntax(value);
        String[] split = getSplitValues(value);
        prepareToParse(split);

        return getZonedDateTimeRange(split);
    }

    private void validateRangeSyntax(String value) {
        if (!hasTwoComponents(value)) {
            ParseException pe = new ParseException("Wrong datetime range syntax.");
            log.warn("Wrong datetime range syntax. Expected two components separated by colon.", pe);
            throw pe;
        }
    }

    private boolean hasTwoComponents(String value) {
        return value.length() % 2 == 1 && value.length() > 1;
    }

    private String[] getSplitValues(String value) {
        int colonIndex = (value.length() - 1) / 2;

        return new String[] { value.substring(0, colonIndex), value.substring(colonIndex + 1, value.length()) };
    }

    private void prepareToParse(String[] split) {
        for (int i = 0; i < split.length; i++) {
            if (areHyphensPresent(split[i])) {
                split[i] = removeHyphens(split[i]);
            }
        }
    }

    private boolean areHyphensPresent(String val) {
        return val.startsWith("\"") && val.endsWith("\"");
    }

    private String removeHyphens(String val) {
        return val.substring(1, val.length() - 1);
    }

    private Range<ZonedDateTime> getZonedDateTimeRange(String[] split) {
        try {
            TemporalAccessor min = dateTimeFormatter.parse(split[MIN_INDEX]);
            TemporalAccessor max = dateTimeFormatter.parse(split[MAX_INDEX]);
            return DateTimeRange.between(ZonedDateTime.from(min), ZonedDateTime.from(max));
        } catch (DateTimeParseException e) {
            ParseException pe = new ParseException("Could not parse date time range", e);
            log.warn("DateTime parse failed.", pe);
            throw pe;
        }
    }
}
