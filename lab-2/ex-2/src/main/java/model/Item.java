package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private final String name;
    private final BigDecimal price;

    public Item(String name, BigDecimal price) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
