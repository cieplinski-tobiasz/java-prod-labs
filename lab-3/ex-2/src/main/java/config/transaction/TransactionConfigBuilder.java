package config.transaction;

import utils.ranges.DateTimeRange;
import utils.ranges.PositiveIntegerRange;
import utils.ranges.Range;

import java.time.ZonedDateTime;

public class TransactionConfigBuilder {
    Range<Integer> purchasesCountRange;
    Range<Integer> customerIdRange;
    Range<ZonedDateTime> dateTimeRange;

    public TransactionConfigBuilder() {
        this.purchasesCountRange = PositiveIntegerRange.between(1, 5);
        this.customerIdRange = PositiveIntegerRange.between(1, 20);
        this.dateTimeRange = DateTimeRange.between(
                ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0),
                ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0)
        );
    }

    public TransactionConfigBuilder withCountRange(Range<Integer> range) {
        this.purchasesCountRange = range;
        return this;
    }

    public TransactionConfigBuilder withCustomerIdRange(Range<Integer> range) {
        this.customerIdRange = range;
        return this;
    }

    public TransactionConfigBuilder withDateTimeRange(Range<ZonedDateTime> range) {
        this.dateTimeRange = range;
        return this;
    }

    public TransactionConfig build() {
        return new TransactionConfig(this);
    }
}

