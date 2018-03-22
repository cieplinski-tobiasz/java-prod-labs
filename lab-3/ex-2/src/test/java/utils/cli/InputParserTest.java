package utils.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InputParserTest {
    @Test
    public void shouldThrowIllegalStateExceptionWhenParseNotPossible() throws ParseException {
        CommandLineParser mockedParser = mock(CommandLineParser.class);
        when(mockedParser.parse(any(), any())).thenThrow(ParseException.class);
        InputParser uut = new InputParser(mockedParser, new String[]{});

        Throwable thrown = catchThrowable(uut::parseCommandLine);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasCauseExactlyInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseValidArguments() {
        InputParser uut = new InputParser(new DefaultParser(), new String[]{
                "-customerIds", "1:20",
                "-dateRange", "\"2018-03-08T00:00:00.000-0100\":\"2018-03-08T23:59:59.999-0100\"",
                "-itemsFile", "items.csv",
                "-itemsCount", "5:15",
                "-itemsQuantity", "1:30",
                "-eventsCount", "1000"
        });

        CommandLine result = uut.parseCommandLine();

        assertThat(result.hasOption("customerIds")).isTrue();
        assertThat(result.hasOption("dateRange")).isTrue();
        assertThat(result.hasOption("itemsFile")).isTrue();
        assertThat(result.hasOption("itemsCount")).isTrue();
        assertThat(result.hasOption("itemsQuantity")).isTrue();
        assertThat(result.hasOption("eventsCount")).isTrue();
    }
}