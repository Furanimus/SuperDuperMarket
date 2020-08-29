package course.java.sdm.engine;

import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.LocationsMatrix;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.exceptions.LocationAlreadyRegisteredException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//TODO Singleton
    public class VendorManager {
        private Map<Integer, Vendor> idToVendor;
        private LocationsMatrix locationsMatrix;
        private static VendorManager instance = null;
        public Map<Integer, Vendor> getIdToVendor() {
            return idToVendor;
        }

    private VendorManager() {
        idToVendor = new HashMap<>();
        locationsMatrix = new LocationsMatrix();
    }

    public static synchronized VendorManager getInstance() {
            if (instance == null) {
                instance = new VendorManager();
            }
            return instance;
        }

        public boolean isLocationExist(Location toInsert) {
            return locationsMatrix.getLocation(toInsert.getX(), toInsert.getY());
        }

        public void addVendor(Vendor toAdd) {
            int x = toAdd.getLocation().getX();
            int y =toAdd.getLocation().getY();
            if(isLocationExist(toAdd.getLocation())) {
                throw new LocationAlreadyRegisteredException(String.format("Vendor already exists this location: (%d,%d)", x, y));
            } else {
                locationsMatrix.addLocation(x, y);
                idToVendor.put(toAdd.getId(), toAdd);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            return sb.toString();
        }

        public double averageProductPrice(int id) {
            double sum = 0;
            int numOfStoresThatSellTheItem = howManyVendorsSellItem(id);
            for (Vendor vendor : idToVendor.values()) {
                if (vendor.getIdToPrice().containsKey(id)) {
                    sum+= vendor.getIdToPrice().get(id);
                }
            }
            return sum / numOfStoresThatSellTheItem;
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
        public ArrayList<Map<String, Object>> getVendorsInfo() {
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            for (Vendor vendor : idToVendor.values()) {
                Map<String, Object> vendorInfo = new TreeMap<>();
                vendorInfo.put("Id",vendor.getId());
                vendorInfo.put("Name",vendor.getName());
                vendorInfo.put("Products", getVendorProducts(vendor));
                //vendorInfo.put("Past Orders",TODO);
                vendorInfo.put("PPK",vendor.getPPK());
                //vendorInfo.put("summed PPK",TODO);

                //TODO add count how many times the item was sold.
                result.add(vendorInfo);
            }
            return result;
        }

        public  Map<Integer, Object> getVendorProducts(Vendor vendor) {
            Map<Integer, Object> products = new TreeMap<>();
            Map<Integer, Product> allProducts = SystemManagerSingleton.getInstance().getProductsMap();
            Map<Integer, Integer> prices = vendor.getIdToPrice();

            for (int productId : prices.keySet()) {
                Product product = allProducts.get(productId);
                Map<String, Object> currentProduct = new TreeMap<>();
                currentProduct.put("Id", product.getId());
                currentProduct.put("Name",product.getName());
                currentProduct.put("Purchase Category", product.getPurchaseCategory());
                currentProduct.put("Price", prices.get(productId));
                //TODO currentProduct.put("Time Sold", PLACE_HOLDER);
                products.put(productId, currentProduct);
            }
            return products;

            /*
            for(Integer productId : vendor.getIdToPrice().values()) {

            }
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

             */
        }
    }
