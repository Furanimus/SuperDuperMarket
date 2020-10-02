package course.java.sdm.engine.managers;

import course.java.sdm.engine.entities.*;

import java.util.*;

public class EngineManagerSingleton {
    private String filePath;
    private boolean isFileLoaded = false;
    private boolean isLastFileLoadedSuccessfully = false;
    private static EngineManagerSingleton instance = null;
    private final StoreManagerSingleton vendorManager;
    private final OrderManagerSingleton orderManager;
    private final ProductManagerSingleton productManager;
    private final CustomerManagerSingleton customerManager;

    private Map<Integer,SmartProduct> idToProduct;

    public boolean getIsFileLoaded() {
        return isFileLoaded;
    }

    public void setLastFileLoadedSuccessfully(boolean lastFileLoadedSuccessfully) {
        isLastFileLoadedSuccessfully = lastFileLoadedSuccessfully;
    }

    public void fileLoaded() {
        this.isFileLoaded = true;
    }

    public String getFilePath() {
        return filePath;
    }

    public CustomerManagerSingleton getCustomerManager() { return customerManager; }

    public OrderManagerSingleton getOrderManager() { return orderManager; }

    public StoreManagerSingleton getVendorManager() {
        return vendorManager;
    }

    public void setFilePath(String path) {
        filePath = path;
    }

    public ArrayList<SmartProduct> getProductsList() {
        return new ArrayList<>(idToProduct.values());
    }

    private EngineManagerSingleton() {
        vendorManager = StoreManagerSingleton.getInstance();
        orderManager = OrderManagerSingleton.getInstance();
        customerManager = CustomerManagerSingleton.getInstance();
        productManager = ProductManagerSingleton.getInstance();
        idToProduct = new TreeMap<>();
    }

    public static synchronized EngineManagerSingleton getInstance() {
        if (instance == null) {
            instance = new EngineManagerSingleton();
        }
        return instance;
    }

    public Map<Integer, SmartProduct> getProductsMap() {
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

    public void updateSmartProductsStatistics() {
        for(SmartProduct product : idToProduct.values()) {
            product.updateStatistics();
        }
    }

    public boolean getIsLastFileLoadedSuccessfully() {
        return isLastFileLoadedSuccessfully;
    }
}