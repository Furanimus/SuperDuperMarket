package course.java.sdm.engine.entities;

public class Product {
    protected int id;
    protected String name;
    protected String purchaseCategory;

    public Product(int id, String name, String isWeightable) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = isWeightable;
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
                        " | id: " + id +
                        " | Purchase category: " + purchaseCategory;
        return  result;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }
}
