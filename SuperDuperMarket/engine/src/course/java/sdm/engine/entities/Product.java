package course.java.sdm.engine.entities;

public class Product {
    protected int id;
    protected String name;
    protected boolean isWeightable; //TODO

    public Product(int id, String name, boolean isWeightable) {
        this.id = id;
        this.name = name;
        this.isWeightable = isWeightable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String result = "Product name: " + name + " | id: " + id +
                " | Purchase category: " ;
        if(this.isWeightable) {
            result += "Weight";
        } else{
            result += "Quantity";
        }
        return  result+"\n";
    }

    public boolean isWeightable() {
        return isWeightable;
    }
}
