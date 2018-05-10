package model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public final class Transaction {
    private final int id;
    @NonNull
    private final ZonedDateTime timestamp;
    private final int customerId;
    @NonNull
    private final List<Purchase> purchases;

    public BigDecimal getSum() {
        return purchases.stream()
                .map(p -> p.getItem().getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
