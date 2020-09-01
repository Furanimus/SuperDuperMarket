package course.java.sdm.engine.managers;

import com.sun.org.apache.xpath.internal.operations.Or;
import course.java.sdm.engine.entities.Order;
import course.java.sdm.engine.utils.MyUtils;
import sun.plugin.com.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderManager {

    private Map<Integer, Order> idToOrder = new TreeMap<>();

    public List<Order> getOrdersByVendorId(int vendorId) {
        List<Order> result = new ArrayList<>();
        for (Order order : idToOrder.values()) {
            if (order.getWhereFrom().getId() == vendorId) {
                result.add(order);
            }
        }
        return  result;
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

    public String getAllOrdersStr() {
        StringBuilder sb = new StringBuilder();
        for(Order order : idToOrder.values()) {
            sb.append("Order Id:");
            sb.append(order.getId());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Date of order:");
            sb.append(order.getDate());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Order was made from store:");
            sb.append(order.getWhereFrom().getName());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Store Id:");
            sb.append(order.getWhereFrom().getId());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Number of different items purchased:");
            sb.append(idToOrder.size());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Total items purchased:");
            sb.append(order.getTotalItemCount());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Products price: ");
            sb.append(order.getTotalProductsPrice());
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Delivery price:");
            sb.append(MyUtils.formatNumber(order.getDeliveryCost()));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Total:");
            sb.append(MyUtils.formatNumber(order.getTotalPrice()));
            sb.append("\n");
        }

        return sb.toString();
    }

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
}
