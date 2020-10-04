package course.java.sdm.engine.managers;

import course.java.sdm.engine.entities.*;
import course.java.sdm.engine.exceptions.LocationAlreadyRegisteredException;

import java.util.*;

//TODO Singleton
    public class StoreManagerSingleton {
        private static StoreManagerSingleton instance = null;
        private Map<Integer, Store> idToStore;
        private LocationsMatrix locationsMatrix;

        private StoreManagerSingleton() {
            idToStore = new HashMap<>();
            locationsMatrix = new LocationsMatrix();
        }

        public Store getVendor(int id) {
                return idToStore.getOrDefault(id, null);
        }

        public static synchronized StoreManagerSingleton getInstance() {
            if (instance == null) {
                instance = new StoreManagerSingleton();
            }
            return instance;
        }

        public boolean isLocationExist(Location toInsert) {
            return locationsMatrix.getLocation(toInsert.getX(), toInsert.getY());
        }

        public void addVendor(Store toAdd) {
            int x = toAdd.getLocation().getX();
            int y =toAdd.getLocation().getY();
            if(isLocationExist(toAdd.getLocation())) {
                throw new LocationAlreadyRegisteredException(String.format("Vendor already exists this location: (%d,%d)", x, y));
            } else {
                locationsMatrix.addLocation(x, y);
                idToStore.put(toAdd.getId(), toAdd);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            return sb.toString();
        }

        public List<Product> getStoreProductSoldIn(int productId) {
            List<Product> result = new ArrayList<>();
            for (Store store : idToStore.values()) {
                if (store.isSellItem(productId)) {
                    result.add(store.getProduct(productId));
                }
            }
            return result;
        }

        public double averageProductPrice(int id) {
            double sum = 0;
            int numOfStoresThatSellTheItem = howManyVendorsSellItem(id);
            for (Store store : idToStore.values()) {
                if (store.isSellItem(id)) {
                    sum+= store.getPrice(id);
                }
            }
            return sum / numOfStoresThatSellTheItem;
        }

        public int howManyVendorsSellItem(int productId) {
                int result = 0;
                for (Store store : idToStore.values()) {
                    if (store.isSellItem(productId)) {
                        result++;
                    }
                }
                return result;
        }

        public ArrayList<Map<String, Object>> getVendorsInfo() {
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            for (Store store : idToStore.values()) {
                Map<String, Object> vendorInfo = new TreeMap<>();
                vendorInfo.put("Id", store.getId());
                vendorInfo.put("Name", store.getName());
                vendorInfo.put("Products", getVendorProductsInfo(store));
                vendorInfo.put("PPK", store.getPPK());

                List<Order> orders = OrderManagerSingleton.getInstance().getOrdersByStoreId(store.getId());
                vendorInfo.put("Past Orders",orders);
                double totalProducts = 0;
                int summedDeliveryPrice = 0;
                for (Order order : orders) {
                    summedDeliveryPrice += order.getDeliveryCost();
                    totalProducts += order.getTotalItemCount();
                }
                vendorInfo.put("Total Sold Products", totalProducts);
                vendorInfo.put("Summed Delivery Price",summedDeliveryPrice);

                result.add(vendorInfo);
            }
            return result;
        }

        public  Map<Integer, Object> getVendorProductsInfo(Store store) {
            Map<Integer, Object> products = new TreeMap<>();
            Map<Integer, SmartProduct> allProducts = EngineManagerSingleton.getInstance().getProductsMap();
            Map<Integer, Integer> prices = store.getIdToPrice();

            for (int productId : prices.keySet()) {
                Product product = allProducts.get(productId);
                Map<String, Object> currentProduct = new TreeMap<>();
                currentProduct.put("Id", product.getId());
                currentProduct.put("Name",product.getName());
                currentProduct.put("Purchase Category", product.getPurchaseCategory());
                currentProduct.put("Price", prices.get(productId));
                currentProduct.put("Time Sold", OrderManagerSingleton.getInstance().howMuchProductWasSold(productId, store.getId()));
                products.put(productId, currentProduct);
            }
            return products;
        }

        public void resetData() {
            idToStore = new TreeMap<>();
            locationsMatrix.reset();
        }

    public ArrayList<Store> getStoresList() {
        return new ArrayList<>(idToStore.values());
    }
}
