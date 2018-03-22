package config.purchase;

import utils.ranges.Range;

public class PurchaseConfig {
    private final Range<Integer> quantityRange;

    PurchaseConfig(PurchaseConfigBuilder purchaseConfigBuilder) {
        this.quantityRange = purchaseConfigBuilder.quantityRange;
    }

    public Range<Integer> getQuantityRange() {
        return quantityRange;
    }
}
