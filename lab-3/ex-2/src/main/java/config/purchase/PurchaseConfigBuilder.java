package config.purchase;

import utils.ranges.PositiveIntegerRange;
import utils.ranges.Range;

public class PurchaseConfigBuilder {
    Range<Integer> quantityRange;

    public PurchaseConfigBuilder() {
        this.quantityRange = PositiveIntegerRange.between(1, 5);
    }

    public PurchaseConfigBuilder withQuantityRange(Range<Integer> range) {
        this.quantityRange = range;
        return this;
    }

    public PurchaseConfig build() {
        return new PurchaseConfig(this);
    }
}
