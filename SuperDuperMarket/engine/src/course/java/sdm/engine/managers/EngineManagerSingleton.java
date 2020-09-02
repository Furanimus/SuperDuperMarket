package course.java.sdm.engine.managers;

import course.java.sdm.engine.entities.*;

import java.util.*;

public class EngineManagerSingleton {
    private String filePath;
    private boolean isFileLoaded = false;
    private static EngineManagerSingleton instance = null;
    private final VendorManagerSingleton vendorManager;
    private final OrderManagerSingleton orderManager;
    private Map<Integer,Product> idToProduct;

    public boolean getIsFileLoaded() {
        return isFileLoaded;
    }

    public void fileLoaded() {
        this.isFileLoaded = true;
    }

    public String getFilePath() {
        return filePath;
    }

    public OrderManagerSingleton getOrderManager() { return orderManager; }

    public VendorManagerSingleton getVendorManager() {
        return vendorManager;
    }

    public void setFilePath(String path) {
        filePath = path;
    }

    private EngineManagerSingleton() {
        vendorManager = VendorManagerSingleton.getInstance();
        orderManager = OrderManagerSingleton.getInstance();
        idToProduct = new TreeMap<>();
    }

    public static synchronized EngineManagerSingleton getInstance() {
        if (instance == null) {
            instance = new EngineManagerSingleton();
        }
        return instance;
    }

    public Map<Integer, Product> getProductsMap() {
        return idToProduct;
    }

    public Product getProduct(int id) {
        return idToProduct.getOrDefault(id, null);
    }

    public ArrayList<Map<String, Object>> getProductsDescriptionAndStatistics() {
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (Product product : idToProduct.values()) {
            Map<String, Object> productInfo = new TreeMap<>();
            productInfo.put("Id",product.getId());
            productInfo.put("Name",product.getName());
            productInfo.put("Purchase Category",product.getPurchaseCategory());
            productInfo.put("Number Of Stores that Sell the product",vendorManager.howManyVendorsSellItem(product.getId()));
            productInfo.put("Product Average Price",vendorManager.averageProductPrice(product.getId()));
            //TODO add count how many times the item was sold.
            productInfo.put("Times Sold", orderManager.howMuchProductWasSold(product.getId()));
            result.add(productInfo);
        }
        return result;
    }

    public void resetData() {
        idToProduct = new TreeMap<>();
        vendorManager.resetData();
        orderManager.reset();
    }
}