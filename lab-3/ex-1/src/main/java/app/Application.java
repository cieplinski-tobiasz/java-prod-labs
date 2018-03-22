package app;

import com.external.PaymentsService;
import com.internal.DiscountCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private DiscountCalculator discountCalculator = new DiscountCalculator();
    private PaymentsService paymentsService = new PaymentsService();

    public void run(BigDecimal ticketPrice, int customerAge, int customerId, int companyId) {
        BigDecimal amount = discountCalculator.calculateDiscount(ticketPrice, customerAge);
        paymentsService.makePayment(customerId, companyId, amount);
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            logger.error("Bad arguments");
            throw new IllegalStateException();
        }

        BigDecimal ticketPrice = null;
        int customerAge = 0;
        int customerId = 0;
        int companyId = 0;

        try {
            ticketPrice = new BigDecimal(args[0]);
            customerAge = Integer.valueOf(args[1]);
            customerId = Integer.valueOf(args[2]);
            companyId = Integer.valueOf(args[3]);
        } catch (NumberFormatException e) {
            logger.error("Could not parse arguments", e);
            System.out.println("Błędne argumenty.");
        }

        Application application = new Application();
        application.run(ticketPrice, customerAge, customerId, companyId);
    }
}
