package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

public class TransactionService {
    private final PersistenceLayer persistenceLayer;

    public TransactionService(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    public boolean saveTransaction(int customerId, String itemName, int quantity) {
        Customer customer = persistenceLayer.getCustomerById(customerId);
        Item item = persistenceLayer.getItemByName(itemName);
        return persistenceLayer.saveTransaction(customer, item, quantity);
    }
}
