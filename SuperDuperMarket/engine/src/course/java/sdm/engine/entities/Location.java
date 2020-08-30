package course.java.sdm.engine.entities;

import course.java.sdm.engine.exceptions.LocationOutOfBoundsException;

import java.util.Objects;

public class Location {
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location(int x, int y) {
        if (validateLocationBounds(x, y)) {
            this.x = x;
            this.y = y;
        } else {
            throw new LocationOutOfBoundsException("Location is not within range [1,50]");
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}


