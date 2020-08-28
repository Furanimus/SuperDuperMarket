package course.java.sdm.engine;

import course.java.sdm.engine.entities.*;

import java.text.DecimalFormat;
import java.util.*;

public class SystemManagerSingleton {

    private final DecimalFormat D2F = new DecimalFormat("#.##");
    public final String SEPARATOR = " | ";
    public String formatNumber(double num) {
        return D2F.format(num);
    }

    /*
        private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void main(String[] args) {

        double input = 3.14159265359;
        System.out.println("double : " + input);
        System.out.println("double : " + df2.format(input));    //3.14
     */
    private final int NUM_OF_COLS = 6;
    private String filePath;

    private boolean isFileLoaded = false;

    private static SystemManagerSingleton instance = null;
    private final VendorManager vendorManager;
    private final Map<Integer,Product> idToProduct;
    //private List<Vendor> vendorList;

    public boolean getIsFileLoaded() {
        return isFileLoaded;
    }

    public void fileLoaded() {
        this.isFileLoaded = true;
    }

    public String getFilePath() {
        return filePath;
    }

    public VendorManager getVendorManager() {
        return vendorManager;
    }

    public void setFilePath(String path) {
        filePath = path;
    }

    private SystemManagerSingleton() {
        vendorManager = new VendorManager();
        idToProduct = new HashMap<>();

        /*
        //TODO remove
        try {
            justAnExample();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        */
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
            result.add(productInfo);
        }
        return result;
    }
}