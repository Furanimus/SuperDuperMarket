package course.java.sdm.engine;

import course.java.sdm.engine.players.*;
import java.util.*;

public class SystemManagerSingleton {
    private static SystemManagerSingleton instance = null;
    private Map<Integer,Product> idToProduct;
    private List<Vendor> vendorList;

    private SystemManagerSingleton() {
        vendorList = new ArrayList<>();
        idToProduct = new HashMap<>();
    }

    public static synchronized SystemManagerSingleton getInstance() {
        if (instance == null) {
            instance = new SystemManagerSingleton();
        }
        return instance;
    }

    public List<Vendor> getVendorList() {
        return vendorList;
    }
    public Map<Integer, Product> getProductsMap() {
        return idToProduct;
    }

    public void viewAvailableProducts() {
        System.out.println("product");
    }
}
