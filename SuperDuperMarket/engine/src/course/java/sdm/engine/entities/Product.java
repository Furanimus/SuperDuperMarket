package course.java.sdm.engine.entities;

public class Product {//implements ICountable, IWeightable{
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

    public boolean isWeightable() {
        return isWeightable;
    }
}
