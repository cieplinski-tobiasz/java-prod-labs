package model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Transaction {
    private final int id;
    private final ZonedDateTime zonedDateTime;
    private final int customerId;
    private final List<Purchase> purchases;

    public Transaction(int id, ZonedDateTime zonedDateTime, int customerId, List<Purchase> purchases) {
        Objects.requireNonNull(zonedDateTime);
        Objects.requireNonNull(purchases);

        if (purchases.isEmpty()) {
            throw new IllegalArgumentException("Purchases list must not be empty.");
        }

        this.id = id;
        this.zonedDateTime = zonedDateTime;
        this.customerId = customerId;
        this.purchases = purchases;
    }

    public int getId() {
        return id;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<Purchase> getPurchases() {
        return Collections.unmodifiableList(purchases);
    }

    public BigDecimal getSum() {
        return purchases.stream()
                .map(p -> p.getItem().getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
