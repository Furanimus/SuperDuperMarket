package course.java.sdm.engine;

import course.java.sdm.engine.entities.*;
import java.util.*;

public class SystemManagerSingleton {
    private String filePath = "";
    private boolean isFileLoaded = false;

    private static SystemManagerSingleton instance = null;
    private final VendorManager vendorManager = new VendorManager();
    private Map<Integer,Product> idToProduct = new HashMap<>();
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
        justAnExample();

    }

    private void justAnExample() {
        vendorManager.addVendor( new Vendor(1,"Bashari Store", 1.2, new Location(5,7)));
        vendorManager.addVendor( new Vendor(800000,"Babits Store", 1.2, new Location(4,21)));

        //*************** Bad inputs **************
        //vendorManager.addVendor( new Vendor(800000,"Fisher Store", 1.2, new Location (5,7)));
        //vendorManager.addVendor( new Vendor(43,"Kronen Store", 1.2, new Location(542,21))); //Not good because of values
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

    public void viewAvailableProducts() {
        System.out.println("product BLABLA");
    }
}
