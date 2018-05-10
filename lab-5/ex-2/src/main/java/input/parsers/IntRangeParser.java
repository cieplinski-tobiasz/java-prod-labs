package input.parsers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import utils.exceptions.ParseException;
import utils.ranges.PositiveIntegerRange;
import utils.ranges.Range;

@Component
@Slf4j
public final class IntRangeParser implements Parser<Range<Integer>> {
    private static final String ARGUMENT_SEPARATOR = ":";
    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 1;
    private static final int RANGE_ARGUMENTS_AMOUNT = 2;

    @Override
    public Range<Integer> parse(@NonNull String parameter) {
        String[] split = parameter.split(ARGUMENT_SEPARATOR);
        validateRangeSyntax(split);
        return getIntegerRangeOrThrowException(split);
    }

    private void validateRangeSyntax(String[] split) {
        if (split.length != RANGE_ARGUMENTS_AMOUNT) {
            ParseException pe = new ParseException("Wrong int range syntax.");
            log.warn("Wrong integer range syntax. Expected format is number:number.", pe);
            throw pe;
        }
    }

    private Range<Integer> getIntegerRangeOrThrowException(String[] split) {
        try {
            Integer min = Integer.valueOf(split[MIN_INDEX]);
            Integer max = Integer.valueOf(split[MAX_INDEX]);
            return PositiveIntegerRange.between(min, max);
        } catch (NumberFormatException e) {
            ParseException pe = new ParseException("Wrong int range components.");
            log.warn("Integer range has invalid values. Values must be integers.", pe);
            throw pe;
        }
    }
}
