package course.java.sdm.engine.entities;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class Customer implements Locatable {
    private int id;
    private String name;
    private Location location;
    private String locationStr;

    private Map<Integer, Order> idToOrderCustomerMade;
    private int numOfOrders;
    private double avgOrderPriceWithoutDelivery;
    private double avgDeliveryPrice;

    public String getLocationStr() {
        return locationStr;
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }

    public double getAvgOrderPriceWithoutDelivery() {
        return avgOrderPriceWithoutDelivery;
    }

    public double getAvgDeliveryPrice() {
        return avgDeliveryPrice;
    }

    public void setLocation(Location location) {
        this.location = location;
        locationStr = location.toString();
    }

    @Override
    public String toString() {
        return  name + " (" + id + ")";
    }

    public Customer(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        setLocation(location);
    }

    public int getNumOfOrdersMade() {
        return idToOrderCustomerMade.size();
    }
/*
    public double avgPriceOfOrdersWithoutDelivery() {
        double result = 0;
        if (idToOrderCustomerMade != null) {
            for (Order order : idToOrderCustomerMade.values()) {
                result += order.getTotalProductsPrice();
            }
            result /= idToOrderCustomerMade.size();
        }
        return result;
    }
 */

    public double avgPriceCalculator(ToDoubleFunction<Order> orderAccessor) {
        double result = 0;
        if(idToOrderCustomerMade.size() != 0) {
            result = idToOrderCustomerMade.values().stream()
                    .mapToDouble(orderAccessor)
                    .average()
                    .getAsDouble();
        }
        return result;
        /*
        if (idToOrderCustomerMade != null) {
            for (Order order : idToOrderCustomerMade.values()) {
                result += orderAccessor.applyAsDouble(order);
            }
            result /= idToOrderCustomerMade.size();
        }
        return result;
         */
    }


    //double sum = avgPriceCalculator(Order::getDeliveryCost);
    //double sum1 = avgPriceCalculator(Order::getTotalProductsPrice);

    public void addOrder(Order orderToAdd) {
        if (idToOrderCustomerMade == null) {
            idToOrderCustomerMade = new TreeMap<>();
        }
        idToOrderCustomerMade.put(orderToAdd.getId(), orderToAdd);
        updateOrderStatistics();
        avgDeliveryPrice = avgPriceCalculator(Order::getDeliveryCost);
        avgOrderPriceWithoutDelivery = avgPriceCalculator(Order::getTotalProductsPrice);

    }

    private void updateOrderStatistics() {
        numOfOrders = idToOrderCustomerMade.size();

    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Integer getId() {
        return id;
    }
}
