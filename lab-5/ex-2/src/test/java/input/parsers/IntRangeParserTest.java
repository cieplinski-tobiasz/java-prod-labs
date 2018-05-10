package input.parsers;

import org.junit.Test;
import utils.exceptions.ParseException;
import utils.ranges.Range;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class IntRangeParserTest {
    @Test
    public void shouldParseThrowNullPointerExceptionWhenParameterIsNull() {
        IntRangeParser uut = new IntRangeParser();
        String parameter = null;

        Throwable thrown = catchThrowable(() -> uut.parse(parameter));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenRangeHasMoreThanTwoComponents() {
        IntRangeParser uut = new IntRangeParser();
        String parameter = "2:4:1";

        Throwable thrown = catchThrowable(() -> uut.parse(parameter));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenRangeHasNonParsableComponents() {
        IntRangeParser uut = new IntRangeParser();
        String parameter = "a:b";

        Throwable thrown = catchThrowable(() -> uut.parse(parameter));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseNotThrowExceptionsWhenRangeIsValid() {
        IntRangeParser uut = new IntRangeParser();
        String parameter = "1:2";

        Throwable thrown = catchThrowable(() -> uut.parse(parameter));

        assertThat(thrown).doesNotThrowAnyException();
    }

    @Test
    public void shouldParseReturnValidRangeGivenValidParameters() {
        IntRangeParser uut = new IntRangeParser();
        String parameter = "1:2";

        Range<Integer> result = uut.parse(parameter);

        assertThat(result.getMinimum()).isEqualTo(1);
        assertThat(result.getMaximum()).isEqualTo(2);
    }

    @Test
    public void shouldParseNotThrowExceptionsWhenRangeConsistsOfSingleValue() {
        IntRangeParser uut = new IntRangeParser();
        String parameter = "1:1";

        Throwable thrown = catchThrowable(() -> uut.parse(parameter));

        assertThat(thrown).doesNotThrowAnyException();
    }

    @Test
    public void shouldParseReturnValidRangeGivenValidSingleValueRange() {
        IntRangeParser uut = new IntRangeParser();
        String parameter = "1:1";

        Range<Integer> result = uut.parse(parameter);

        assertThat(result.getMinimum()).isEqualTo(1);
        assertThat(result.getMaximum()).isEqualTo(1);
    }

}