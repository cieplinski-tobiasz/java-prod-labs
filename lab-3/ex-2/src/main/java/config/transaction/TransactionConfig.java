package config.transaction;

import utils.ranges.Range;

import java.time.ZonedDateTime;

public class TransactionConfig {
    private final Range<Integer> purchasesCountRange;
    private final Range<Integer> customerIdRange;
    private final Range<ZonedDateTime> dateTimeRange;

    TransactionConfig(TransactionConfigBuilder transactionConfigBuilder) {
        this.purchasesCountRange = transactionConfigBuilder.purchasesCountRange;
        this.customerIdRange = transactionConfigBuilder.customerIdRange;
        this.dateTimeRange = transactionConfigBuilder.dateTimeRange;
    }

    public Range<Integer> getPurchasesCountRange() {
        return purchasesCountRange;
    }

    public Range<Integer> getCustomerIdRange() {
        return customerIdRange;
    }

    public Range<ZonedDateTime> getDateTimeRange() {
        return dateTimeRange;
    }
}
