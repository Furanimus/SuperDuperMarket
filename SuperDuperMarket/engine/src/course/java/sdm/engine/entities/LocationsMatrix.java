package course.java.sdm.engine.entities;

import course.java.sdm.engine.exceptions.LocationAlreadyRegisteredException;

public class LocationsMatrix {
    private int ROWS = 50;
    private int COLS = 50;

    private boolean[][] locationMatrix = new boolean[ROWS][COLS];

    public void addLocation(int x, int y) {
        locationMatrix[x - 1][y - 1] = true;
    }

    public boolean getLocation(int x, int y) {
        return locationMatrix[x-1][y-1];
    }

    public void setLocation(int x, int y) {
        locationMatrix[x-1][y-1] = true;
    }
}
