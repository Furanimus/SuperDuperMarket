package course.java.sdm.engine.players;

import java.awt.*;

public class Location extends Point {

    //if ((x <= 50 && x >= 1) && (y <= 50 && y >= 1)) { TODO
    public Location(int x, int y) {
        super(x, y);
    }

    public double measureDistance(Location other) {
        return Math.sqrt(
                (Math.pow(Math.abs(this.x - other.x),2))
                + (Math.pow(Math.abs(this.y - other.y),2)));
    }
}

