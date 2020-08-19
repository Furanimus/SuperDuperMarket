package course.java.sdm.engine.entities;

import java.awt.*;

public class Location extends Point {

    //if ((x <= 50 && x >= 1) && (y <= 50 && y >= 1)) { TODO
    public Location(int x, int y) {
        super(x, y);
    }

    public boolean validateLocationBounds(int x, int y) {
        return (x <= 50 && x >= 1) && (y <= 50 && y >= 1);
    }

    //Pythagoras
    public double measureDistance(Location other) {
        return Math.sqrt(
                (Math.pow(Math.abs(this.x - other.x),2))
                + (Math.pow(Math.abs(this.y - other.y),2)));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}


