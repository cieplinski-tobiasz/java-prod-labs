package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CustomerMoneyService {
    private final Map<Customer, BigDecimal> kasa;
    private final PersistenceLayer persistenceLayer;

    public CustomerMoneyService(PersistenceLayer persistenceLayer) {
        this.kasa = new HashMap<>();
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
    }

    public BigDecimal getMoney(int customerId) {
        Customer customer = persistenceLayer.getCustomerById(customerId);

        if (kasa.containsKey(customer)) {
            return kasa.get(customer);
        } else {
            kasa.put(customer, BigDecimal.TEN);
            persistenceLayer.saveCustomer(customer);
            return kasa.get(customer);
        }
    }

    public boolean pay(int customerId, BigDecimal amount) {
        Customer customer = persistenceLayer.getCustomerById(customerId);
        BigDecimal money = getMoney(customerId);
        if (money.compareTo(amount) >= 0) {
            kasa.put(customer, money.subtract(amount));
            persistenceLayer.saveCustomer(customer);
            return true;
        }
        return false;
    }

    public void addMoney(int customerId, BigDecimal amount) {
        Customer customer = persistenceLayer.getCustomerById(customerId);
        BigDecimal money = getMoney(customerId);
        persistenceLayer.saveCustomer(customer);
        kasa.put(customer, money.add(amount));
    }
}
