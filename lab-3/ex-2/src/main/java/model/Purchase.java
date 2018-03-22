package model;

import java.util.Objects;

public class Purchase {
    private final Item item;
    private final int quantity;

    public Purchase(Item item, int quantity) {
        Objects.requireNonNull(item);

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
