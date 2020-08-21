package course.java.sdm.engine;

import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.LocationsMatrix;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.exceptions.LocationAlreadyRegisteredException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    //TODO Singleton
    public class VendorManager {

        //private List<Location> vendorLocations = new ArrayList<>();
        private Map<Integer, Vendor> idToVendor = new HashMap<>();
        private LocationsMatrix locationsMatrix = new LocationsMatrix();

        public Map<Integer, Vendor> getIdToVendor() {
            return idToVendor;
        }

        /*
            public Vendor getVendor(Location location) {
                return locationToVendor.get(location);
            }

            //private Map<Location, Vendor> locationToVendor = new HashMap<>();


            public List<Location> getvendorLocations() {
                return vendorLocations;
            }

            public Map<Location, Vendor> getLocationToVendor() {
                return locationToVendor;
            }
        */
    private boolean isLocationExist(Location toInsert) {
        return locationsMatrix.getLocation(toInsert.getX(), toInsert.getY());
    }

    public void addVendor(Vendor toAdd) {
        if(isLocationExist(toAdd.getLocation())) {
            throw new LocationAlreadyRegisteredException("Location already exists");
        } else {
            locationsMatrix.addLocation(toAdd.getLocation().getX(), toAdd.getLocation().getY());
            idToVendor.put(toAdd.getId(), toAdd);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }
}
