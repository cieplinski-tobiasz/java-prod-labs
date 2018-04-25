package input.parsers;

import model.Item;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.exceptions.ParseException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CSVItemParserTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void shouldParseThrowNullPointerExceptionWhenCsvIsNull() {
        CSVItemParser uut = new CSVItemParser();

        Throwable thrown = catchThrowable(() -> uut.parse(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenCsvHasMoreThanTwoComponents() {
        CSVItemParser uut = new CSVItemParser();

        Throwable thrown = catchThrowable(() -> uut.parse("first,second,third"));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenItemNameDoesNotStartWithHyphen() {
        CSVItemParser uut = new CSVItemParser();

        Throwable thrown = catchThrowable(() -> uut.parse("nohyphen\",1.30"));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenItemNameDoesNotEndWithHyphen() {
        CSVItemParser uut = new CSVItemParser();

        Throwable thrown = catchThrowable(() -> uut.parse("\"nohyphen,1.30"));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenItemNameIsEmpty() {
        CSVItemParser uut = new CSVItemParser();

        Throwable thrown = catchThrowable(() -> uut.parse("\"\",1.30"));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseThrowParseExceptionWhenPriceParseFails() {
        CSVItemParser uut = new CSVItemParser();

        Throwable thrown = catchThrowable(() -> uut.parse("\"item\",cant-parse-this"));

        assertThat(thrown).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseReturnItemWhenArgumentsAreValid() {
        CSVItemParser uut = new CSVItemParser();

        Item result = uut.parse("\"przedmiot\",1");

        assertThat(result.getName()).isEqualTo("przedmiot");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.ONE);
    }

}