package course.java.sdm.engine.entities;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Sale {
    private Pair<Integer, Double> productIdYouNeedToAmountYouNeed;
    private Map<Integer, Pair<Double, Integer>> productsIdToAmountAndPriceYouGet;

    public Sale() {
        productsIdToAmountAndPriceYouGet = new HashMap<>();
    }

    public void setProductYouNeedToAmountYouNeed(int productId, double amount) {
        productIdYouNeedToAmountYouNeed = new Pair<>(productId, amount);
    }

    public boolean checkIfOrderEligibleForSale(Order order) {
        boolean result = false;
        int productId = productIdYouNeedToAmountYouNeed.getKey();
        if (order.isContainProduct(productId)) {
           if(order.getProductAmount(productId) >= productIdYouNeedToAmountYouNeed.getValue()) {
               result = true;
           }
        }
        return result;
    }

    public void addItemToSale(int itemId, double quantity, int forAdditional) {
        productsIdToAmountAndPriceYouGet.put(itemId, new Pair<>(quantity, forAdditional));
    }
}