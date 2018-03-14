package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {
    private final TransactionService transactionService;
    private final CustomerMoneyService moneyService;
    private final DiscountWrapper discountWrapper;
    private final PersistenceLayer persistenceLayer;

    public SellingService(TransactionService transactionService, CustomerMoneyService moneyService,
                          DiscountWrapper discountWrapper, PersistenceLayer persistenceLayer) {
        this.transactionService = transactionService;
        this.moneyService = moneyService;
        this.discountWrapper = discountWrapper;
        this.persistenceLayer = persistenceLayer;
    }

    public boolean sell(String itemName, int quantity, int customerId) {
        Item item = persistenceLayer.getItemByName(itemName);
        Customer customer = persistenceLayer.getCustomerById(customerId);

        BigDecimal price = countOverallPrice(item, quantity, customer);

        if (moneyService.pay(customerId, price)) {
            if (transactionService.saveTransaction(customerId, itemName, quantity)) {
                return true;
            } else {
                moneyService.addMoney(customerId, price);
                return false;
            }
        } else {
            return false;
        }
    }

    private BigDecimal countOverallPrice(Item item, int quantity, Customer customer) {
        BigDecimal price = countDiscountPrice(item, customer).multiply(BigDecimal.valueOf(quantity));

        if (discountWrapper.isWeekendPromotion() && price.compareTo(DiscountWrapper.MINIMUM_PRICE_FOR_DISCOUNT) >= 0) {
            price = price.subtract(DiscountWrapper.WEEKEND_DISCOUNT);
        }

        return price;
    }

    private BigDecimal countDiscountPrice(Item item, Customer customer) {
        return item.getPrice().subtract(discountWrapper.getDiscountForItem(item, customer));
    }
}
