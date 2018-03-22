package json.gson.factories;

import com.google.gson.Gson;
import json.gson.factories.GsonFactory;
import model.Item;
import model.Purchase;
import model.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomAdaptersGsonFactoryTest {
    private Item item = new Item("test", BigDecimal.TEN);
    private Purchase purchase = new Purchase(item, 1);

    @Test
    public void shouldSerializePurchaseInASpecificWay() {
        GsonFactory gsonFactory = new CustomAdaptersGsonFactory();
        Gson uut = gsonFactory.createGson();

        String result = uut.toJson(purchase);

        assertThat(result).isEqualTo("{\"name\":\"test\",\"quantity\":1,\"price\":10}");
    }

    @Test
    public void shouldSerializeTransactionInASpecificWay() {
        Transaction transaction = new Transaction(
                0,
                ZonedDateTime.of(2000, 1, 1, 1, 0, 0, 0, ZoneId.of("Z")),
                0,
                Collections.singletonList(purchase)
        );
        GsonFactory gsonFactory = new CustomAdaptersGsonFactory();
        Gson uut = gsonFactory.createGson();

        String result = uut.toJson(transaction);

        assertThat(result).isEqualTo("{\"id\":0,\"timestamp\":\"2000-01-01T01:00:00.000+0000\",\"customer_id\":0,\"items\":[{\"name\":\"test\",\"quantity\":1,\"price\":10}],\"sum\":10}");
    }

}