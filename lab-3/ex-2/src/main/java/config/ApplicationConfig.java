package config;

import config.generator.GeneratorConfig;
import config.item.ItemConfig;
import config.purchase.PurchaseConfig;
import config.transaction.TransactionConfig;

public class ApplicationConfig {
    public final GeneratorConfig generatorConfig;
    public final ItemConfig itemConfig;
    public final PurchaseConfig purchaseConfig;
    public final TransactionConfig transactionConfig;

    public ApplicationConfig(GeneratorConfig generatorConfig, ItemConfig itemConfig,
                             PurchaseConfig purchaseConfig, TransactionConfig transactionConfig) {
        this.generatorConfig = generatorConfig;
        this.itemConfig = itemConfig;
        this.purchaseConfig = purchaseConfig;
        this.transactionConfig = transactionConfig;
    }
}
