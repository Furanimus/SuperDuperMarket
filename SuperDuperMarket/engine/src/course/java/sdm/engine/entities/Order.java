package course.java.sdm.engine.entities;

import course.java.sdm.engine.managers.EngineManagerSingleton;
import java.time.LocalDate;
import java.util.*;

public class Order {

    private Map<Integer, Double> productIdToAmount = new HashMap<>();
    private Map<Integer, Store> whereFrom = new HashMap<>();

    private int orderingCustomerId;
    private static int orderIdTracker;
    private int orderId;
    private double totalPrice;
    private double deliveryCost;
    private double totalProductsPrice;
    private Location targetLocation;
    private boolean isDynamic;
    private LocalDate localDate;

    public void setIsDynamic(boolean isDynamic) {
        this.isDynamic = isDynamic;
    }

    public Order() {
        orderId = orderIdTracker++;
    }

    public Order(int orderingCustomerId, LocalDate date) {
        this.orderingCustomerId = orderingCustomerId;
        orderId = orderIdTracker++;
        localDate = date;
    }

    public Order(int orderingCustomerId, Store whereFrom, LocalDate date) {
        this.whereFrom.put(whereFrom.getId(), whereFrom);
        this.orderingCustomerId = orderingCustomerId;
        this.localDate = date;
        orderId = orderIdTracker++;
    }

    public void setTotalPrice() {
        totalPrice = totalProductsPrice + deliveryCost;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public double getTotalProductsPrice() {
        return totalProductsPrice;
    }

    public int getOrderingCustomerId() {
        return orderingCustomerId;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setDeliveryCost() {
        deliveryCost = whereFrom.values().stream()
                .mapToDouble(store -> targetLocation.measureDistance(store.getLocation()) * store.getPPK())
                .sum();
        //deliveryCost = whereFrom.getPPK() * targetLocation.measureDistance(whereFrom.getLocation());
    }

    public void setTotalProductsPrice() {
        for (Store store : whereFrom.values()) {
            for (Product product : store.getProductsMap().values()) {
                totalProductsPrice += product.getPrice() * getProductAmount(product.getId());
            }
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
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

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public ArrayList<Store> getWhereFrom() {
        return new ArrayList<>(whereFrom.values());
    }

    public int getDifferentProductCount() {
        return productIdToAmount.size();
    }

    /*
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
 */

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

    public boolean getIsDynamic() {
        return isDynamic;
    }

    public void setCustomerId(int orderingCustomerId) {
        this.orderingCustomerId = orderingCustomerId;
    }
}