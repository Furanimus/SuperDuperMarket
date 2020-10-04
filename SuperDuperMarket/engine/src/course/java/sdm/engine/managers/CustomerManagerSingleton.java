package course.java.sdm.engine.managers;
import course.java.sdm.engine.entities.Customer;
import course.java.sdm.engine.entities.SmartProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerManagerSingleton {

    private static CustomerManagerSingleton instance;
    private Map<Integer, Customer> idToCustomer;

    public void setIdToCustomer(Map<Integer, Customer> idToCustomer) {
        this.idToCustomer = idToCustomer;
    }

    public Customer getCustomer(int id) {
        return idToCustomer.get(id);
    }

    private CustomerManagerSingleton() {
        idToCustomer = new TreeMap<>();
    }

    public void addCustomer(Customer customerToAdd) {
        idToCustomer.put(customerToAdd.getId(),customerToAdd);
    }

    public ArrayList<Customer> getCustomerList() {
        return new ArrayList<>(idToCustomer.values());
    }

    public static CustomerManagerSingleton getInstance() {
        if (instance == null) {
            instance = new CustomerManagerSingleton();
        }
        return instance;
    }
}
