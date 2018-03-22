package utils.generators;

import model.Item;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomItemGeneratorTest {
    @Mock
    private Item item;

    @Mock
    private Random random;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowNullPointerExceptionWhenItemsListIsNull() {
        Throwable thrown = catchThrowable(() -> new RandomItemGenerator(null, random));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenRandomIsNull() {
        List<Item> items = getOneElementItemList();

        Throwable thrown = catchThrowable(() -> new RandomItemGenerator(items, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenListIsEmpty() {
        List<Item> items = Collections.emptyList();

        Throwable thrown = catchThrowable(() -> new RandomItemGenerator(items, random));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnElementFromListGivenOneElementList() {
        when(random.nextInt(anyInt())).thenAnswer(i -> ((Integer) i.getArgument(0)) - 1);
        List<Item> oneItem = getOneElementItemList();
        RandomItemGenerator uut = new RandomItemGenerator(oneItem, random);

        Item result = uut.generateItem();

        assertThat(result).isEqualTo(oneItem.get(0));
        assertThat(oneItem).contains(result);
    }

    @Test
    public void shouldReturnElementFromListGivenMoreThanOneElementList() {
        when(random.nextInt(anyInt())).thenAnswer(i -> ((Integer) i.getArgument(0)) - 1);
        List<Item> moreThanOneItem = getMoreThanOneElementItemList();
        RandomItemGenerator uut = new RandomItemGenerator(moreThanOneItem, random);

        Item result = uut.generateItem();

        assertThat(moreThanOneItem).contains(result);
    }

    private List<Item> getOneElementItemList() {
        return Collections.singletonList(mock(Item.class));
    }

    private List<Item> getMoreThanOneElementItemList() {
        return Arrays.asList(
                mock(Item.class),
                mock(Item.class),
                mock(Item.class)
        );
    }

}