package course.java.sdm.engine.entities;

import course.java.sdm.engine.utils.MyUtils;

public class Product {
    protected int id;
    protected String name;
    protected String purchaseCategory;
    private int price = -1;

    public Product(int id, String name, String purchaseCategory) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
    }

    public Product(Product other, int price) {
        this.id = other.id;
        this.name = other.name;
        this.purchaseCategory = other.purchaseCategory;
        this.price = price;
    }
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String result = "Product name: " + name +
                MyUtils.STRING_SEPARATOR + "id: " + id +
                MyUtils.STRING_SEPARATOR + "Purchase category: " + purchaseCategory +
                MyUtils.STRING_SEPARATOR + "Price: " + price;
        return  result;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }
}
