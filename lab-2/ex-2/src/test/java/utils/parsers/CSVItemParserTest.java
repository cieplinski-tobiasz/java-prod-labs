package utils.parsers;

import model.Item;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.BufferedReader;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class CSVItemParserTest {
    @Mock
    private BufferedReader bufferedReader;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowIllegalStateExceptionWhenCannotReadFile() {
        when(bufferedReader.lines()).thenThrow(UncheckedIOException.class);
        CSVItemParser uut = new CSVItemParser(bufferedReader);

        Throwable thrown = catchThrowable(uut::parse);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasCauseExactlyInstanceOf(UncheckedIOException.class);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenThereAreNotTwoComponentsInCsvFile() {
        when(bufferedReader.lines()).thenReturn(Stream.of("h1,h2,h3", "test,test,test"));
        CSVItemParser uut = new CSVItemParser(bufferedReader);

        Throwable thrown = catchThrowable(uut::parse);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasMessage("Csv file format incorrect.");
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenCsvFileIsEmpty() {
        when(bufferedReader.lines()).thenReturn(Stream.of("h1,h2,h3"));
        CSVItemParser uut = new CSVItemParser(bufferedReader);

        Throwable thrown = catchThrowable(uut::parse);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasMessage("Csv file empty.");
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenItemNameIsEmpty() {
        when(bufferedReader.lines()).thenReturn(Stream.of("h1,h2", "\"\",2.30"));
        CSVItemParser uut = new CSVItemParser(bufferedReader);

        Throwable thrown = catchThrowable(uut::parse);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasMessage("Item name cannot be empty.");
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenPriceCannotBeParsed() {
        when(bufferedReader.lines()).thenReturn(Stream.of("h1,h2", "\"test\",2.3fasd0"));
        CSVItemParser uut = new CSVItemParser(bufferedReader);

        Throwable thrown = catchThrowable(uut::parse);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasCauseExactlyInstanceOf(NumberFormatException.class);
    }

    @Test
    public void shouldParseValidCsv() {
        when(bufferedReader.lines()).thenReturn(
                Stream.of("name,price", "\"serdeczne\",1.1", "\"pozdrowienia\",2.2")
        );
        Item[] items = {
                new Item("serdeczne", BigDecimal.valueOf(1.1)),
                new Item("pozdrowienia", BigDecimal.valueOf(2.2))
        };
        CSVItemParser uut = new CSVItemParser(bufferedReader);

        List<Item> result = uut.parse();

        assertThat(result).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(items);
    }
}