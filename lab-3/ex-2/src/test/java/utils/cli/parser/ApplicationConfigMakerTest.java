package utils.cli.parser;


import org.apache.commons.cli.CommandLine;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.validators.config.GeneratorConfigValidator;

import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ApplicationConfigMakerTest {
    @Mock
    private CommandLine commandLine;

    @Mock
    private GeneratorConfigValidator generatorConfigValidator;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldNotThrowExceptionsWhenCommandLineIsEmpty() {
        when(commandLine.getOptionValue(anyString())).thenReturn(null);
        when(commandLine.getOptionValues(anyString())).thenReturn(null);
        ApplicationConfigMaker uut = new ApplicationConfigMaker(commandLine, generatorConfigValidator);

        Throwable notThrown = catchThrowable(uut::getApplicationConfig);

        assertThat(notThrown).doesNotThrowAnyException();
    }

    @Test
    public void shouldNotThrowExceptionsWhenCommandLineHasValidArguments() {
        mockValidCommandLine();
        ApplicationConfigMaker uut = new ApplicationConfigMaker(commandLine, generatorConfigValidator);

        Throwable notThrown = catchThrowable(uut::getApplicationConfig);

        assertThat(notThrown).doesNotThrowAnyException();
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenCommandLineHasInvalidEventsCount() {
        when(commandLine.getOptionValue(ArgumentMatchers.eq("eventsCount"))).thenReturn("1000fsad");
        ApplicationConfigMaker uut = new ApplicationConfigMaker(commandLine, generatorConfigValidator);

        Throwable thrown = catchThrowable(uut::getApplicationConfig);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasCauseExactlyInstanceOf(NumberFormatException.class);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenCommandLineHasInvalidIntegerRange() {
        when(commandLine.getOptionValues(ArgumentMatchers.eq("itemsQuantity")))
                .thenReturn(new String[]{"\"2018-03-08T00:00:00.000-0100\"", "\"2018-03-08T23:59:5sdfgsfg9.999-0100\""});
        ApplicationConfigMaker uut = new ApplicationConfigMaker(commandLine, generatorConfigValidator);

        Throwable thrown = catchThrowable(uut::getApplicationConfig);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasCauseExactlyInstanceOf(NumberFormatException.class);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenCommandLineHasBadDateTimeFormat() {
        when(commandLine.getOptionValues(ArgumentMatchers.eq("dateRange"))).thenReturn(new String[]{"1", "2sda" });
        ApplicationConfigMaker uut = new ApplicationConfigMaker(commandLine, generatorConfigValidator);

        Throwable thrown = catchThrowable(uut::getApplicationConfig);

        assertThat(thrown).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenCommandLineHasInvalidDateTimeRange() {
        when(commandLine.getOptionValues(ArgumentMatchers.eq("dateRange")))
                .thenReturn(new String[]{"123456789012345678901234567890", "123456789012345678901234567890" });
        ApplicationConfigMaker uut = new ApplicationConfigMaker(commandLine, generatorConfigValidator);

        Throwable thrown = catchThrowable(uut::getApplicationConfig);

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasCauseExactlyInstanceOf(DateTimeParseException.class);
    }

    private void mockValidCommandLine() {
        when(commandLine.getOptionValue(ArgumentMatchers.eq("eventsCount"))).thenReturn("1000");
        when(commandLine.getOptionValue(ArgumentMatchers.eq("outDir"))).thenReturn("./test");
        when(commandLine.getOptionValue(ArgumentMatchers.eq("itemsFile"))).thenReturn("items.csv");
        when(commandLine.getOptionValues(ArgumentMatchers.eq("itemsQuantity"))).thenReturn(new String[]{"1", "2" });
        when(commandLine.getOptionValues(ArgumentMatchers.eq("itemsCount"))).thenReturn(new String[]{"1", "2" });
        when(commandLine.getOptionValues(ArgumentMatchers.eq("customerIds"))).thenReturn(new String[]{"1", "2" });
        when(commandLine.getOptionValues(ArgumentMatchers.eq("dateRange")))
                .thenReturn(new String[]{"\"2018-03-08T00:00:00.000-0100\"", "\"2018-03-08T23:59:59.999-0100\"" });
    }
}