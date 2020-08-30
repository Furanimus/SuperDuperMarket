package course.java.sdm.engine.entities;

public class OrderedProduct extends Product {

    private double amount;
    private double totalPrice;

    public OrderedProduct(int id, String name, String purchaseCategory, double amount) {
        super(id, name, purchaseCategory);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void addToAmount(double toAdd) {
        amount += toAdd;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}