package course.java.sdm.engine.players;

public abstract class Product implements ICountable, IWeightable{
    protected int id;
    protected String name;

    protected Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
