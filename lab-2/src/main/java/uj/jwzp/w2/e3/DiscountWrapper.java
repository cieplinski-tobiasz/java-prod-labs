package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;

import java.math.BigDecimal;

public interface DiscountWrapper {
    BigDecimal WEEKEND_DISCOUNT = BigDecimal.valueOf(3);
    BigDecimal MINIMUM_PRICE_FOR_DISCOUNT = BigDecimal.valueOf(5);

    default BigDecimal getDiscountForItem(Item item, Customer customer) {
        return DiscountsConfig.getDiscountForItem(item, customer);
    }

    default boolean isWeekendPromotion() { return DiscountsConfig.isWeekendPromotion(); }
}
