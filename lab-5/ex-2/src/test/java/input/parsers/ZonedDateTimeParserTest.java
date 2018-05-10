package input.parsers;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.exceptions.ParseException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ZonedDateTimeParserTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private DateTimeFormatter dateTimeFormatter;

    @Test
    public void shouldConstructionThrowNullPointerExceptionWhenDateTimeFormatterIsNull() {
        Throwable thrown = catchThrowable(() -> new ZonedDateTimeParser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldParseThrowNullPointerExceptionWhenParameterIsNull() {
        ZonedDateTimeParser uut = new ZonedDateTimeParser(dateTimeFormatter);

        Throwable thrown = catchThrowable(() -> uut.parse(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenValueDoesNotHaveTwoComponents() {
        ZonedDateTimeParser uut = new ZonedDateTimeParser(dateTimeFormatter);

        Throwable thrown = catchThrowable(() ->
                uut.parse("2018-03-08T00:00:00.000-0100:" +
                        "2018-03-08T00:00:00.000-0100:2018-03-08T00:00:00.000-0100")
        );

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenComponentLengthIsTooShort() {
        ZonedDateTimeParser uut = new ZonedDateTimeParser(dateTimeFormatter);

        Throwable thrown = catchThrowable(() -> uut.parse(":"));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenParsingFails() {
        when(dateTimeFormatter.parse(any())).thenThrow(DateTimeParseException.class);
        ZonedDateTimeParser uut = new ZonedDateTimeParser(dateTimeFormatter);

        Throwable thrown = catchThrowable(() -> uut.parse("\"can't\":\"parsethis\""));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseCallDateTimeFormatterWhenParsing() {
        when(dateTimeFormatter.parse(any())).thenReturn(ZonedDateTime.now());
        ZonedDateTimeParser uut = new ZonedDateTimeParser(dateTimeFormatter);

        uut.parse("2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100");

        verify(dateTimeFormatter).parse("2018-03-08T00:00:00.000-0100");
        verify(dateTimeFormatter).parse("2018-03-08T23:59:59.999-0100");
    }

    @Test
    public void shouldParseRemoveRedundantHyphensWhenParsing() {
        when(dateTimeFormatter.parse(any())).thenReturn(ZonedDateTime.now());
        ZonedDateTimeParser uut = new ZonedDateTimeParser(dateTimeFormatter);

        uut.parse("\"2018-03-08T00:00:00.000-0100\":\"2018-03-08T23:59:59.999-0100\"");

        verify(dateTimeFormatter).parse("2018-03-08T00:00:00.000-0100");
        verify(dateTimeFormatter).parse("2018-03-08T23:59:59.999-0100");
    }

}