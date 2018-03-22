package model;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PurchaseTest {
    @Mock
    private Item item;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void shouldThrowNullPointerExceptionWhenItemIsNull() {
        Throwable thrown = catchThrowable(() -> new Purchase(null, 1));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenQuantityIsNotPositive() {
        Throwable thrown = catchThrowable(() -> new Purchase(item, -1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldGetItemReturnItem() {
        Purchase uut = new Purchase(item, 1);

        Item result = uut.getItem();

        assertThat(result).isEqualTo(item);
    }

    @Test
    public void shouldGetQuantityReturnQuantity() {
        Purchase uut = new Purchase(item, 2);

        int result = uut.getQuantity();

        assertThat(result).isEqualTo(2);
    }

}