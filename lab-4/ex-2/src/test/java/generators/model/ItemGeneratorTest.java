package generators.model;

import generators.NumberGenerator;
import model.Item;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ItemGeneratorTest {
    @Mock
    private NumberGenerator numberGenerator;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldConstructorThrowNullPointerExceptionWhenNumberGeneratorIsNull() {
        Throwable thrown = catchThrowable(() -> new ItemGenerator(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGenerateItemThrowIllegalArgumentExceptionWhenListIsEmpty() {
        List<Item> items = Collections.emptyList();
        ItemGenerator uut = new ItemGenerator(numberGenerator);

        Throwable thrown = catchThrowable(() -> uut.generateItemFromList(items));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnElementFromListGivenOneElementList() {
        List<Item> oneItem = getOneElementItemList();
        when(numberGenerator.nextInt(anyInt())).thenReturn(0);
        ItemGenerator uut = new ItemGenerator(numberGenerator);

        Item result = uut.generateItemFromList(oneItem);

        assertThat(result).isEqualTo(oneItem.get(0));
        assertThat(oneItem).contains(result);
        verify(numberGenerator, times(1)).nextInt(0);
    }

    private List<Item> getOneElementItemList() {
        return Collections.singletonList(mock(Item.class));
    }

    @Test
    public void shouldReturnElementFromListGivenMoreThanOneElementList() {
        when(numberGenerator.nextInt(anyInt())).thenReturn(1);
        List<Item> moreThanOneItem = getMoreThanOneElementItemList();
        ItemGenerator uut = new ItemGenerator(numberGenerator);

        Item result = uut.generateItemFromList(moreThanOneItem);

        assertThat(moreThanOneItem).contains(result);
        assertThat(result).isEqualTo(moreThanOneItem.get(1));
    }

    private List<Item> getMoreThanOneElementItemList() {
        return Arrays.asList(
                mock(Item.class),
                mock(Item.class),
                mock(Item.class)
        );
    }
}