package config.item;

public class ItemConfig {
    private final String itemsFilename;

    ItemConfig(ItemConfigBuilder itemConfigBuilder) {
        itemsFilename = itemConfigBuilder.itemsFilename;
    }

    public String getItemsFilename() {
        return itemsFilename;
    }
}
