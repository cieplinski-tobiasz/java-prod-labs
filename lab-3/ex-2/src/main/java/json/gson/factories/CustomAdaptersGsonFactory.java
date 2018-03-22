package json.gson.factories;

import json.gson.adapters.PurchaseAdapter;
import json.gson.adapters.TransactionAdapter;
import model.Purchase;
import model.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CustomAdaptersGsonFactory implements json.gson.factories.GsonFactory {
    @Override
    public Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Purchase.class, new PurchaseAdapter())
                .registerTypeAdapter(Transaction.class, new TransactionAdapter())
                .create();
    }
}
