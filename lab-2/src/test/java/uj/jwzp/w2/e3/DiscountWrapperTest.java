package uj.jwzp.w2.e3;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import uj.jwzp.w2.e3.external.DiscountsConfig;

import java.math.BigDecimal;

public class DiscountWrapperTest {

    private static class DiscountWrapperDefault implements DiscountWrapper {

    }

    @Test
    public void shouldDiscountBeTheSameAsConfigDiscount() {
        // given
        DiscountWrapper uut = new DiscountWrapperDefault();
        Item item = new Item("item", BigDecimal.TEN);
        Customer customer = new Customer(1, "name", "address");
        BigDecimal discount = DiscountsConfig.getDiscountForItem(item, customer);

        // when
        BigDecimal result = uut.getDiscountForItem(item, customer);

        Assertions.assertThat(result).isEqualTo(discount);
    }

    @Test
    public void shouldWeekendPromotionBeTheSameAsConfig() {
        //given
        DiscountWrapperDefault uut = new DiscountWrapperDefault();
        Item item = new Item("item", BigDecimal.TEN);
        Customer customer = new Customer(1, "name", "address");
        boolean weekendPromotion = DiscountsConfig.isWeekendPromotion();

        // when
        boolean result = uut.isWeekendPromotion();

        // then
        Assertions.assertThat(result).isEqualTo(weekendPromotion);
    }

    @Test
    public void shouldQualifyForPromotionWhenWeekendAppropriate() {
        // given
        DiscountWrapperDefault uut = new DiscountWrapperDefault();
        Item item = new Item("item", BigDecimal.TEN);
        Customer customer = new Customer(1, "name", "address");

        // when
    }



}