package course.java.sdm.engine;

import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.LocationsMatrix;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.exceptions.LocationAlreadyRegisteredException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    //TODO Singleton
    public class VendorManager {

        private final String SEPARATOR = " | ";
        //private List<Location> vendorLocations = new ArrayList<>();
        private Map<Integer, Vendor> idToVendor = new HashMap<>();
        private LocationsMatrix locationsMatrix = new LocationsMatrix();

        public Map<Integer, Vendor> getIdToVendor() {
            return idToVendor;
        }

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

        public double avarageProductPrice(int id) {
            double sum = 0;
            int numOfStoresThatSellTheItem = howManyVendorsSellItem(id);
            for (Vendor vendor : idToVendor.values()) {
                if (vendor.getIdToPrice().containsKey(id)) {
                    sum+= vendor.getIdToPrice().get(id);
                }
            }
            return sum/numOfStoresThatSellTheItem;
        }

        public int howManyVendorsSellItem(int productId) {
                int result = 0;
                for (Vendor vendor : idToVendor.values()) {
                    if (vendor.getIdToPrice().containsKey(productId)) {
                        result++;
                    }
                }
                return result;
        }

        //TODO
        public ArrayList<String> getProductStatisticsAgainstVendors() {
            List<String> result = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();

            for (Vendor vendor : idToVendor.values()) {
                sb.append(vendor.toString());
                sb.append(SEPARATOR);
                sb.append("The store sells the following products: \n");
                sb.append(getStoreProductsInformationString(vendor));

                //sb.append(separator);
                //sb.append(""); //TODO count how many times the item was sold.
                sb.append("\n");
                result.add(sb.toString());
                sb.delete(0, sb.length()); //clears sb
            }


            return (ArrayList<String>) result;
        }

        private String getStoreProductsInformationString(Vendor vendor) {
            StringBuilder sb = new StringBuilder();
            Map<Integer, Product> idToProduct = SystemManagerSingleton.getInstance().getProductsMap();
            Map<Integer, Integer> idToPrice = vendor.getIdToPrice();

            for (int productId : idToPrice.keySet()) {
                sb.append(idToProduct.get(productId).toString());
                sb.append(SEPARATOR);
                sb.append("Product price: " + idToPrice.get(productId));
                //TODO sb.append(numOfProductsSoldFromVendor(productId))
                sb.append("\n");
            }

            return sb.toString();
        }
    }
