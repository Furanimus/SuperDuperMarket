package course.java.sdm.engine.entities;
import java.util.*;

public class Vendor {
    private int id;
    private String name;
    private double PPK;
    private Location location;
    private Map<Integer, Double> idToPrice;

    public Vendor() {
    }

    public void addProduct(Integer id, double price) {
        idToPrice.put(id, price);
    }

    public Vendor (int id, String name, double PPK, Location location) {
        this.id = id;
        this.name = name;
        this.PPK = PPK;
        this.location = location;
        idToPrice = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getPPK() {
        return PPK;
    }

    public Location getLocation() {
        return location;
    }
}
