package course.java.sdm.engine.entities;

import course.java.sdm.engine.managers.EngineManagerSingleton;
import course.java.sdm.engine.utils.MyUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {
    public final DateFormat DF = new SimpleDateFormat(DATE_FORMAT);
    private static final String DATE_FORMAT = "dd/mm-hh:mm";
    private final int DAYS = 31;
    private final int MONTHS = 12;
    private final int HOURS = 24;
    private final int MINUTES = 60;
    //private Customer orderingCustomer;
    private static int orderIdTracker;
    private int orderId;
    private double totalPrice;
    private double deliveryCost;
    private double totalProductsPrice;
    private Date date;
    private Map<Integer, Double> productIdToAmount = new HashMap<>();
    private Vendor whereFrom;

    public Order(Vendor whereFrom) {
        this.whereFrom = whereFrom;
        orderId = orderIdTracker++;
    }

    public void setTotalPrice() {
        totalPrice = totalProductsPrice + deliveryCost;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public DateFormat getDF() {
        return DF;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public double getTotalProductsPrice() {
        return totalProductsPrice;
    }

    public void setDeliveryCost() {
        deliveryCost = whereFrom.getPPK() * targetLocation.measureDistance(whereFrom.getLocation());
    }

    public void setTotalProductsPrice() {
        for (Product product : whereFrom.getProductsMap().values()) {
            totalProductsPrice += product.getPrice() * getProductAmount(product.getId());
        }
    }


    public Date getDate() {
        return date;
    }

    public int  getId() {
        return orderId;
    }

    public double getProductAmount(int productId) {
        return productIdToAmount.getOrDefault(productId, 0.0);
    }

    public boolean isContainProduct(int productId) {
        return productIdToAmount.containsKey(productId);
    }

    public double getTotalItemCount() {
        double totalCount = 0;
        for (double itemCount : productIdToAmount.values()) {
            totalCount += itemCount;
        }
        return totalCount;
    }

    private Location targetLocation;

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public Order() {
        orderId = orderIdTracker++;
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
        }
        return  result;
    }

    public boolean setDate(String dateFormat) throws Exception {
        boolean result = false;
        try {
            if (dateChecker(dateFormat)) {
                this.date = DF.parse(dateFormat);
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

    public int getDifferentProductCount() {
        return productIdToAmount.size();
    }

    public static String getDATE_FORMAT() {
        return DATE_FORMAT;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int id : productIdToAmount.keySet()) {
            Product product = whereFrom.getProduct(id);
            sb.append("Product Id: ");
            sb.append(product.getId());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Product Name: ");
            sb.append(product.getName());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Purchase category: ");
            sb.append(product.getPurchaseCategory());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Product Price: ");
            sb.append(product.getPrice());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Amount taken: ");
            sb.append(productIdToAmount.get(id));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Total price: ");
            sb.append(productIdToAmount.get(id) * product.getPrice());
            sb.append("\n");
        }
        int ppk = whereFrom.getPPK();
        sb.append("store PPK: ");
        sb.append(ppk);
        sb.append(MyUtils.STRING_SEPARATOR);
        double distance = targetLocation.measureDistance(whereFrom.getLocation());
        sb.append("Distance from you: ");
        sb.append(MyUtils.formatNumber(distance));
        sb.append(MyUtils.STRING_SEPARATOR);
        sb.append("Delivery price: ");
        sb.append(MyUtils.formatNumber(ppk * distance));

        return sb.toString();
    }

    public void addProduct(int productId, double amountToAdd) {
        if (productIdToAmount.containsKey(productId)){
            productIdToAmount.put(productId, productIdToAmount.get(productId) + amountToAdd);
        } else {
            productIdToAmount.put(productId, amountToAdd);
        }
    }

    public void adjustWeightAmounts() {
        for (int productId : productIdToAmount.keySet()) {
            Product product = EngineManagerSingleton.getInstance().getProductsMap().get(productId);
            if (product.getPurchaseCategory().equals("Weight")) {
                productIdToAmount.put(productId, 1.0);
            }

        }
    }
}