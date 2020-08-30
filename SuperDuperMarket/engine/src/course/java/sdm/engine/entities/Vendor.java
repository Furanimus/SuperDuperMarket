package course.java.sdm.engine.entities;
import java.util.*;

public class Vendor {
    private int id;
    private String name;
    private int PPK;
    private Location location;
    private Map<Integer, Integer> idToPrice;
    //private Map<Integer, Product> idToProduct;

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

    public Vendor() {
    }

    public void addProduct(Integer id, int price) {
        idToPrice.put(id, price);
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
    }

    public String getName() {
        return name;
    }

    public int getPPK() {
        return PPK;
    }

    public Location getLocation() {
        return location;
    }
}
