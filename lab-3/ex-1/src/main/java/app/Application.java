package app;

import com.external.PaymentsService;
import com.internal.DiscountCalculator;

import java.math.BigDecimal;

public class Application {
    public static void main(String[] args) {
        DiscountCalculator discountCalculator = new DiscountCalculator();
        PaymentsService paymentsService = new PaymentsService();

        discountCalculator.calculateDiscount(BigDecimal.ONE, 1);
        paymentsService.makePayment(1, 2, BigDecimal.ONE);
    }
}
