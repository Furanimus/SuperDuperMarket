package course.java.sdm.engine;

import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.Vendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorManager {
    private List<Location> vendorsLocation = new ArrayList<>();
    private Map<Location, Vendor> locationToVendor = new HashMap<>();

    public Vendor getVendor(Location location) {
        return locationToVendor.get(location);
    }

    private boolean isLocationExist(Location toInsert) {
        boolean isExist = false;
        for(Location location : vendorsLocation) {
            if (toInsert.equals(location)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public void addVendor(Vendor toAdd) {
        if(!isLocationExist(toAdd.getLocation())) {
            vendorsLocation.add(toAdd.getLocation());
            locationToVendor.put(toAdd.getLocation(), toAdd);
        } else {

        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }
}
