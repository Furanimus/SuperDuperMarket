package course.java.sdm.engine.entities;

import course.java.sdm.engine.managers.OrderManagerSingleton;
import course.java.sdm.engine.managers.StoreManagerSingleton;

public class SmartProduct extends Product {
    private final StoreManagerSingleton storeManager;
    private final OrderManagerSingleton orderManager;

    protected double avgPrice;
    protected int numberOfStoresSoldIn;

    public double getAvgPrice() {
        return avgPrice;
    }

    public int getNumberOfStoresSoldIn() {
        return numberOfStoresSoldIn;
    }

    public double getTimesSold() {
        return timesSold;
    }

    protected double timesSold;

    public SmartProduct(int id, String name, String purchaseCategory) {
        super(id, name, purchaseCategory);
        storeManager = StoreManagerSingleton.getInstance();
        orderManager = OrderManagerSingleton.getInstance();
    }

    public void setAvgPrice() {
        avgPrice = storeManager.howManyVendorsSellItem(id);
    }

    public void setNumberOfStoresSoldIn() {
        numberOfStoresSoldIn = storeManager.getStoreProductSoldIn(id).size();
    }

    public void setTimesSold() {
        timesSold = orderManager.howMuchProductWasSold(id);
    }

    public void updateStatistics() {
        setAvgPrice();
        setNumberOfStoresSoldIn();
        setTimesSold();
    }
}
