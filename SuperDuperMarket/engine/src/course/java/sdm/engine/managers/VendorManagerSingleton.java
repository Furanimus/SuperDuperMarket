package course.java.sdm.engine.managers;

import course.java.sdm.engine.entities.*;
import course.java.sdm.engine.exceptions.LocationAlreadyRegisteredException;
import sun.reflect.generics.tree.Tree;

import java.util.*;

//TODO Singleton
    public class VendorManagerSingleton {
        private Map<Integer, Vendor> idToVendor;
        private LocationsMatrix locationsMatrix;
        private static VendorManagerSingleton instance = null;

        public Map<Integer, Vendor> getIdToVendor() {
            return idToVendor;
        }

    private VendorManagerSingleton() {
        idToVendor = new HashMap<>();
        locationsMatrix = new LocationsMatrix();
    }

    public Vendor getVendor(int id) {
            return idToVendor.getOrDefault(id, null);
    }

    public static synchronized VendorManagerSingleton getInstance() {
            if (instance == null) {
                instance = new VendorManagerSingleton();
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
                if (vendor.isSellItem(id)) {
                    sum+= vendor.getPrice(id);
                }
            }
            return sum / numOfStoresThatSellTheItem;
        }

        public int howManyVendorsSellItem(int productId) {
                int result = 0;
                for (Vendor vendor : idToVendor.values()) {
                    if (vendor.isSellItem(productId)) {
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
                vendorInfo.put("Products", getVendorProductsInfo(vendor));
                vendorInfo.put("PPK",vendor.getPPK());

                List<Order> orders = OrderManagerSingleton.getInstance().getOrdersByVendorId(vendor.getId());
                vendorInfo.put("Past Orders",orders);
                double totalProducts = 0;
                int summedDeliveryPrice = 0;
                for (Order order : orders) {
                    summedDeliveryPrice += order.getDeliveryCost();
                    totalProducts += order.getTotalItemCount();
                }
                vendorInfo.put("Total Sold Products", totalProducts);
                vendorInfo.put("Summed Delivery Price",summedDeliveryPrice);

                //TODO add count how many times the item was sold.
                result.add(vendorInfo);
            }
            return result;
        }

        public  Map<Integer, Object> getVendorProductsInfo(Vendor vendor) {
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
                currentProduct.put("Time Sold", OrderManagerSingleton.getInstance().howMuchProductWasSold(productId,vendor.getId()));
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

//    public void setNewVendorMap(Map<Integer, Vendor> newMap) {
//            idToVendor = newMap;
//    }

    public void resetData() {
        idToVendor = new TreeMap<>();
        locationsMatrix.reset();
    }
}
