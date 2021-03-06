package model;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import java.math.BigDecimal;

@Getter
@Slf4j
public final class Purchase {
    private final Item item;
    private final int quantity;

    public Purchase(@NonNull Item item, int quantity) {
        if (quantity <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("Quantity must be greater than zero.");
            log.warn("Invalid quantity.", iae);
            throw iae;
        }

        this.item = item;
        this.quantity = quantity;
    }
}
