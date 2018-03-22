package config.item;

public class ItemConfigBuilder {
    String itemsFilename;

    public ItemConfigBuilder() {
        this.itemsFilename = "items.csv";
    }

    public ItemConfigBuilder withItemsFilename(String itemsFilename) {
        this.itemsFilename = itemsFilename;
        return this;
    }

    public ItemConfig build() {
        ItemConfig itemConfig = new ItemConfig(this);
        return itemConfig;
    }
}
