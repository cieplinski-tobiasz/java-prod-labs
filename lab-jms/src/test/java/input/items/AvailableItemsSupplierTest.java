package input.items;

import input.parsers.CSVItemParser;
import model.Item;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AvailableItemsSupplierTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ResourceReader resourceReader;

    @Mock
    private CSVItemParser csvItemParser;

    @Mock
    private Environment environment;

    @Mock
    private Item item;

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenResourceReaderIsNull() {
        Throwable thrown = catchThrowable(() -> new AvailableItemsSupplier(null, csvItemParser, environment));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenCSVItemParserIsNull() {
        Throwable thrown = catchThrowable(() -> new AvailableItemsSupplier(resourceReader, null, environment));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenEnvironmentIsNull() {
        Throwable thrown = catchThrowable(() -> new AvailableItemsSupplier(resourceReader, csvItemParser, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGetCallEnvironment() {
        AvailableItemsSupplier uut = new AvailableItemsSupplier(resourceReader, csvItemParser, environment);

        uut.get();

        verify(environment, times(1)).getProperty("itemsFile", "items.csv");
    }

    @Test
    public void shouldGetCallResourceReader() {
        when(environment.getProperty(anyString(), anyString())).thenReturn("itemsFile");
        when(resourceReader.getResourceLines(anyString())).thenReturn(Stream.of("\"item\",1"));
        AvailableItemsSupplier uut = new AvailableItemsSupplier(resourceReader, csvItemParser, environment);

        uut.get();

        verify(resourceReader, times(1)).getResourceLines(anyString());
    }

    @Test
    public void shouldGetCallItemParser() {
        when(environment.getProperty(anyString(), anyString())).thenReturn("itemsFile");
        when(resourceReader.getResourceLines(anyString())).thenReturn(Stream.of("item,price", "\"test\",1"));
        AvailableItemsSupplier uut = new AvailableItemsSupplier(resourceReader, csvItemParser, environment);

        uut.get();

        verify(csvItemParser).parse(anyString());
    }

    @Test
    public void shouldGetReturnListOfItemsWhenParametersAreValid() {
        when(environment.getProperty(anyString(), anyString())).thenReturn("itemsFile");
        when(resourceReader.getResourceLines(anyString())).thenReturn(Stream.of("item,price", "\"test\",1"));
        when(item.getName()).thenReturn("test");
        when(item.getPrice()).thenReturn(BigDecimal.ONE);
        when(csvItemParser.parse("\"test\",1")).thenReturn(item);
        AvailableItemsSupplier uut = new AvailableItemsSupplier(resourceReader, csvItemParser, environment);

        List<Item> result = uut.get();

        assertThat(result).hasOnlyOneElementSatisfying(item -> {
            item.getName().equals("test");
            item.getPrice().equals(BigDecimal.ONE);
        });
    }
}