package model;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ItemTest {
    @Mock
    private BigDecimal price;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowNullPointerExceptionWhenNameIsNull() {
        Throwable thrown = catchThrowable(() -> new Item(null, price));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPriceIsNull() {
        Throwable thrown = catchThrowable(() -> new Item("", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldGetNameReturnName() {
        String name = "test";
        Item uut = new Item(name, price);

        String result = uut.getName();

        assertThat(result).isEqualTo(name);
    }

    @Test
    public void shouldGetPriceReturnPrice() {
        Item uut = new Item("", price);

        BigDecimal result = uut.getPrice();

        assertThat(result).isEqualTo(price);
    }
}