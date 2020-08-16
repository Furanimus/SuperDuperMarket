package course.java.sdm.engine.players;
import java.util.*;

public class Vendor {
    private String name;
    private double PPK;
    private Location location;
    Map<Product, Double> products = new HashMap();

    public Vendor (String name, double PPK, Location location) {
        this.name = name;
        this.PPK = PPK;
        this.location = location;
    }
}
