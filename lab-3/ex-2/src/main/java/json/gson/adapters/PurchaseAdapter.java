package json.gson.adapters;

import model.Purchase;
import com.google.gson.*;

import java.lang.reflect.Type;

public class PurchaseAdapter implements JsonSerializer<Purchase> {
    @Override
    public JsonElement serialize(Purchase src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", src.getItem().getName());
        obj.addProperty("quantity", src.getQuantity());
        obj.addProperty("price", src.getItem().getPrice());

        return obj;
    }
}
