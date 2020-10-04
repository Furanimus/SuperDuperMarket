package course.java.sdm.engine.managers;

import course.java.sdm.engine.entities.Order;
import course.java.sdm.engine.entities.Store;
import course.java.sdm.engine.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderManagerSingleton {
    private static OrderManagerSingleton instance;
    private Map<Integer, Order> idToOrder;

    public Map<Integer, Order> getIdToOrder() {
        return idToOrder;
    }

    private OrderManagerSingleton() {
        idToOrder = new TreeMap<>();
    }

    public synchronized static OrderManagerSingleton getInstance() {
        if (instance == null) {
            instance = new OrderManagerSingleton();
        }
        return instance;
    }


    public List<Order> getOrdersByStoreId(int storeId) {
        List<Order> result = new ArrayList<>();
        for (Order order : idToOrder.values()) {
            List<Store> orderStores = order.getWhereFrom();
            for (Store store : orderStores) {
                if (store.getId() == storeId) {
                    result.add(order);
                }
            }

        }
        return result;
    }

    public double howMuchProductWasSold(int productId, int vendorId) {
        double result = 0;
        List<Order> orders = getOrdersByStoreId(vendorId);
        for (Order order : orders) {
            if (order.isContainProduct(productId)) {
                result += order.getProductAmount(productId);
            }
        }
        return result;
    }

    public double howMuchProductWasSold(int productId) {
        double result = 0;
        for (Order order : idToOrder.values()) {
            if (order.isContainProduct(productId)) {
                result += order.getProductAmount(productId);
            }
        }
        return result;
    }

    public void createDynamicOrder(Map<Integer, Double> productIdToAmount) {

    }
/*
    public String getAllOrdersStr() {
        StringBuilder sb = new StringBuilder();
        for(Order order : idToOrder.values()) {
            sb.append("Order Id: ");
            sb.append(order.getId());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Date of order: ");
            sb.append(order.getDate());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Order was made from store: ");
            sb.append(order.getWhereFrom().getName());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Store Id: ");
            sb.append(order.getWhereFrom().getId());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Number of different items purchased: ");
            sb.append(idToOrder.size());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Total items purchased: ");
            sb.append(order.getTotalItemCount());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Products price: ");
            sb.append(order.getTotalProductsPrice());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Delivery price: ");
            sb.append(MyUtils.formatNumber(order.getDeliveryCost()));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Total Order Price: ");
            sb.append(MyUtils.formatNumber(order.getTotalPrice()));
            sb.append("\n");
        }
        if (sb.toString().isEmpty()) {
            sb.append("No registered orders since last file upload");
        }
        return sb.toString();
    }
 */

    public void addOrder(Order order) {
        idToOrder.put(order.getId(), order);
        setOrderCosts(order);
    }

    private void setOrderCosts(Order order) {
        order.setDeliveryCost();
        order.setTotalProductsPrice();
        order.setTotalPrice();
        //order.adjustWeightAmounts();
    }

    public String getOrderInfoString(List<Order> orders) {
        if (orders.size() == 0) {
            return "There are no orders made \n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n{");
        for (Order order : orders) {
            sb.append("Date of order: ");
            sb.append(order.getLocalDate());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Total count of products: ");
            sb.append(order.getTotalItemCount());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Products price: ");
            sb.append(MyUtils.formatNumber(order.getTotalProductsPrice()));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Cost of delivery: ");
            sb.append(MyUtils.formatNumber(order.getDeliveryCost()));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Total order price: ");
            sb.append(MyUtils.formatNumber(order.getTotalPrice()));
            sb.append("\n");
        }
        sb.append("}\n");

        return sb.toString();
    }

    public void reset() {
        idToOrder = new TreeMap<>();
    }
}