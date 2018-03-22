package json.gson.adapters;

import model.Transaction;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class TransactionAdapter implements JsonSerializer<Transaction> {
    private final DateTimeFormatter dateTimeFormatter;

    public TransactionAdapter() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    @Override
    public JsonElement serialize(Transaction src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", src.getId());
        obj.addProperty("timestamp", dateTimeFormatter.format(src.getZonedDateTime()));
        obj.addProperty("customer_id", src.getCustomerId());
        obj.add("items", context.serialize(src.getPurchases()));
        obj.addProperty("sum", src.getSum());

        return obj;
    }
}
