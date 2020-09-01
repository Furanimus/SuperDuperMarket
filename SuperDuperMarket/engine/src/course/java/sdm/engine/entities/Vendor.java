package course.java.sdm.engine.entities;
import java.util.*;

public class Vendor {
    private int id;
    private String name;
    private int PPK;
    private Location location;
    private Map<Integer, Integer> idToPrice;
    private Map<Integer, Product> idToProduct;

    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getIdToPrice() {
        return idToPrice;
    }

    public boolean isSellItem(int id) {
        return idToPrice.containsKey(id);
    }

    public int getPrice(int productId) {
        return idToPrice.getOrDefault(productId, -1);
    }

    public void addProduct(Product product, int price) {
        idToPrice.put(product.getId(), price);
        idToProduct.put(product.getId(), new Product(product,price));
    }

    @Override
    public String toString() {
        return "Store ID: " + id +
               " | Name: " + name +
               " | PPK: " + PPK ;
    }

    public Vendor (int id, String name, int PPK, Location location) {
        this.id = id;
        this.name = name;
        this.PPK = PPK;
        this.location = location;
        idToPrice = new HashMap<>();
        idToProduct = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getPPK() {
        return PPK;
    }

    public Product getProduct(int id) {
        return idToProduct.getOrDefault(id, null);
    }

    public Location getLocation() {
        return location;
    }

    public Map<Integer, Product> getProductsMap() {
        return idToProduct;
    }
}
