package course.java.sdm.engine.managers;

import course.java.sdm.engine.entities.Customer;

import java.util.Map;
import java.util.TreeMap;

public class ProductManagerSingleton {

    private static ProductManagerSingleton instance;
    private Map<Integer, Customer> idToCustomer;


    private ProductManagerSingleton() {
        idToCustomer = new TreeMap<>();
    }



    public static synchronized  ProductManagerSingleton getInstance() {
        if (instance == null) {
            instance = new ProductManagerSingleton();
        }
        return instance;
    }
}
