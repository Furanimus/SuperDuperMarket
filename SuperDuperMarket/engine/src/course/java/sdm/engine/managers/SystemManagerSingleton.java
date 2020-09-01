package course.java.sdm.engine.managers;

import course.java.sdm.engine.entities.*;

import java.util.*;

public class SystemManagerSingleton {
    private String filePath;
    private boolean isFileLoaded = false;
    private static SystemManagerSingleton instance = null;
    private final VendorManagerSingleton vendorManager;
    private final OrderManagerSingleton orderManager;

    private Map<Integer,Product> idToProduct;
    //private final Map<Integer, Order> allOrders;


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

    private SystemManagerSingleton() {
        vendorManager = VendorManagerSingleton.getInstance();
        orderManager = OrderManagerSingleton.getInstance();
        idToProduct = new TreeMap<>();
        //allOrders = new TreeMap<>();

    }

    public static synchronized SystemManagerSingleton getInstance() {
        if (instance == null) {
            instance = new SystemManagerSingleton();
        }
        return instance;
    }

    public Map<Integer, Product> getProductsMap() {
        return idToProduct;
    }

    public void setNewProductsMap(Map<Integer, Product> newMap) {
        idToProduct = newMap;
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
    }
}