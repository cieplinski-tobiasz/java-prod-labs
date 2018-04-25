package model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public final class Item {
    @NonNull
    private final String name;
    @NonNull
    private final BigDecimal price;
}
