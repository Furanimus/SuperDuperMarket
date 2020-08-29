package course.java.sdm.engine.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Order {
    //private Customer orderingCustomer;
    private final int DAYS = 31;
    private final int MONTHS = 12;
    private final int HOURS = 24;
    private final int MINUTES = 60;
    private static final String DATE_FORMAT = "dd/mm-hh:mm";

    private DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    private Date date;
    private List<Product> orderedProducts;
    private Location targetLocation;

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    private static int orderId = 0;

    public Order() {
        orderedProducts = new ArrayList<>();
        orderId = orderId++;
    }

    public static int getOrderId() {
        return orderId;
    }

    public boolean dateChecker(String date) throws Exception, NumberFormatException {
        boolean result = true;
        if(date.matches("\\d{2}/\\d{2}-\\d{2}:\\d{2}")){
            String[] tokens = date.split(":|/|-");
            int[] ints = new int[tokens.length];
            try {
                for (int i = 0; i < tokens.length; i++) {
                    ints[i] = Integer.parseInt(tokens[i]);
                }
            } catch (NumberFormatException e) {
                result = false;
            }
            if (result) {
                if (ints.length != 4) { result = false; }
                else if (ints[0] > DAYS || ints[0] < 1) { throw new Exception("Days must be between 1-31"); }//result = false; } // TODO Optimize
                else if (ints[1] > MONTHS || ints[1] < 1) { throw new Exception("Months must be between 1-12"); }//result = false; }
                else if (ints[2] > HOURS || ints[2] < 1) {throw new Exception("Hours must be between 00-24"); } //result = false; }
                else if (ints[3] > MINUTES || ints[3] < 1) { throw new Exception("Hours must be between 00-60"); } //result = false; }
            }
            else {
                System.out.println("In here!!!!!!");
            }
        }

        return  result;
    }

    public boolean setDate(String dateFormat) throws Exception {
        boolean result = false;
        try {
            if (dateChecker(dateFormat)) {
                this.date = df.parse(dateFormat);
                result = true;
            } else {
                System.out.println("The format was not correct. Please enter a date in the format of " + DATE_FORMAT);
            }
        } catch (NumberFormatException e) {
            System.out.println("The format was not correct. Please enter a date in the format of " + DATE_FORMAT);
        }
        catch (Exception e) {
            throw new Exception("An unknown error occurred while reading order date");
        }
        return result;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public static String getDATE_FORMAT() {
        return DATE_FORMAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(DATE_FORMAT, order.DATE_FORMAT) &&
                orderedProducts.equals(order.orderedProducts) &&
                Objects.equals(df, order.df);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DATE_FORMAT, orderedProducts, df);
    }

    @Override
    public String toString() {
        return "Order{" +
                "DATE_FORMAT='" + DATE_FORMAT + '\'' +
                ", orderedProducts=" + orderedProducts +
                ", dateFormat=" + df +
                '}';
    }

    public void addProduct(Product product) {

    }
}