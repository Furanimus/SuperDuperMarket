package course.java.sdm.engine.players;

import java.util.ArrayList;
import java.util.List;

public class Order {
    //private Customer orderingCustomer;
    List<Product> orderedProducts;

    public Order() {
        orderedProducts = new ArrayList<>();
    }
}