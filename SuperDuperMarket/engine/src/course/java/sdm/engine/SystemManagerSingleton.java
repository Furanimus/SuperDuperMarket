package course.java.sdm.engine;

import course.java.sdm.engine.entities.*;

import java.text.DecimalFormat;
import java.util.*;

public class SystemManagerSingleton {
    private final DecimalFormat D2F = new DecimalFormat("#.##");
    public final String SEPARATOR = " | ";

    public String formatNumer(double num) {
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

    public void justAnExample() {
        //vendorManager.addVendor(new Vendor(1,"Bashari Store", 1.2, new Location(5,7)));
        //vendorManager.addVendor(new Vendor(800000,"Babits Store", 1.2, new Location(4,21)));

        //*************** Bad inputs **************
        //vendorManager.addVendor(new Vendor(800000,"Fisher Store", 1.2, new Location (5,7)));
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

    public ArrayList<String> getProductsDescriptionAndStatistics() {
        List<String> result = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        for (Product product : idToProduct.values()) {
            sb.append(product.toString());
            sb.append(SEPARATOR);
            sb.append("Stores that sell the item: ");
            sb.append(vendorManager.howManyVendorsSellItem(product.getId()));
            sb.append(SEPARATOR);
            sb.append("Average price: ");
            sb.append(D2F.format(vendorManager.avarageProductPrice(product.getId())));
            //sb.append(separator);
            //sb.append(""); //TODO count how many times the item was sold.
            sb.append("\n");
            result.add(sb.toString());
            sb.delete(0, sb.length()); //clears sb
        }
        return (ArrayList<String>) result;
    }
}