package course.java.sdm.engine.entities;

import course.java.sdm.engine.managers.SystemManagerSingleton;
import course.java.sdm.engine.managers.VendorManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {

    //private Customer orderingCustomer;
    private final int DAYS = 31;
    private final int MONTHS = 12;
    private final int HOURS = 24;
    private final int MINUTES = 60;
    private static final String DATE_FORMAT = "dd/mm-hh:mm";
    private VendorManager vendorManager = VendorManager.getInstance();
    private Vendor whereFrom;

    private DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    private Date date;
    //private List<Product> orderedProducts;
    private Map<Integer, Double> productIdToAmount = new HashMap<>();
    ;
    private Location targetLocation;

    public Order(Vendor whereFrom) {
        this.whereFrom = whereFrom;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    private static int orderId = 0;

    public Order() {
        orderId = orderId++;
    }

    public static int getOrderId() {
        return orderId;
    }

    public Vendor getWhereFrom() {
        return whereFrom;
    }

    public boolean dateChecker(String date) throws Exception, NumberFormatException {
        boolean result = true;
        if(date.matches("\\d{2}/\\d{2}-\\d{2}:\\d{2}")){
            String[] tokens = date.split(":|/|-");
            int[] ints = new int[tokens.length];
            try {
                for (int i = 0; i < tokens.length; i++) {
                    ints[i] = Integer.parseInt(tokens[i]);
                }
            } catch (NumberFormatException e) {
                result = false;
            }
            if (result) {
                if (ints.length != 4) { result = false; }
                else if (ints[0] > DAYS || ints[0] < 1) { throw new Exception("Days must be between 1-31"); }//result = false; } // TODO Optimize
                else if (ints[1] > MONTHS || ints[1] < 1) { throw new Exception("Months must be between 1-12"); }//result = false; }
                else if (ints[2] > HOURS || ints[2] < 1) {throw new Exception("Hours must be between 00-24"); } //result = false; }
                else if (ints[3] > MINUTES || ints[3] < 1) { throw new Exception("Hours must be between 00-60"); } //result = false; }
            }
            else {
                System.out.println("In here!!!!!!");
            }
        }

        return  result;
    }

    public boolean setDate(String dateFormat) throws Exception {
        boolean result = false;
        try {
            if (dateChecker(dateFormat)) {
                this.date = df.parse(dateFormat);
                result = true;
            } else {
                System.out.println("The format was not correct. Please enter a date in the format of " + DATE_FORMAT);
            }
        } catch (NumberFormatException e) {
            System.out.println("The format was not correct. Please enter a date in the format of " + DATE_FORMAT);
        }
        catch (Exception e) {
            throw new Exception("An unknown error occurred while reading order date");
        }
        return result;
    }

//    public List<Product> getOrderedProducts() {
//        return orderedProducts;
//    }

    public Map<Integer, Double> getProductIdToAmount() {
        return productIdToAmount;
    }

    public static String getDATE_FORMAT() {
        return DATE_FORMAT;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int id : productIdToAmount.keySet()) {
            Product product = SystemManagerSingleton.getInstance().getProduct(id);
            sb.append("Product Id: ");
            sb.append(product.getId());
            sb.append(SystemManagerSingleton.STRING_SEPARATOR);
            sb.append("Product Name: ");
            sb.append(product.getName());
            sb.append(SystemManagerSingleton.STRING_SEPARATOR);
            sb.append("Purchase category: ");
            sb.append(product.getPurchaseCategory());
            sb.append(SystemManagerSingleton.STRING_SEPARATOR);
            //sb.append("Product Price: ");
            //sb.append(product.getPrice());
//          sb.append(SystemManagerSingleton.STRING_SEPARATOR);
            sb.append(productIdToAmount.get(id));

        }
        return sb.toString();
    }

    public void addProduct(int productId, double amountToAdd) {
        if (productIdToAmount.containsKey(productId)){
            productIdToAmount.put(productId, productIdToAmount.get(productId) + amountToAdd);
        } else {
            productIdToAmount.put(productId, amountToAdd);
        }
    }

    public double calculateTotalPrice(int productId) {
        return 0;
    }
}