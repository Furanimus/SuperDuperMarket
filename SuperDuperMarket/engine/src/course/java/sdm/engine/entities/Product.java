package course.java.sdm.engine.entities;

import course.java.sdm.engine.managers.SystemManagerSingleton;

public class Product {
    protected int id;
    protected String name;
    protected String purchaseCategory;
    //private int price = -1;

    public Product(int id, String name, String purchaseCategory) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
    }

//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public int getPrice() {
//        return -1;
//    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String result = "Product name: " + name +
                SystemManagerSingleton.STRING_SEPARATOR + "id: " + id +
                SystemManagerSingleton.STRING_SEPARATOR + "Purchase category: " + purchaseCategory;
//                if(price != -1) {
//                    result += SystemManagerSingleton.STRING_SEPARATOR + "Price: " + price;
//                }
        return  result;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }
}
