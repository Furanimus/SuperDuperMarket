package course.java.sdm.engine.xml;

import course.java.sdm.engine.exceptions.*;
import course.java.sdm.engine.xml.jaxbobjects.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileValidator {
    SuperDuperMarketDescriptor descriptor;

    public boolean validateAppWise(SuperDuperMarketDescriptor sdmDescriptor) {
        descriptor = sdmDescriptor;
        boolean result = true;
        result = testSDMStores(sdmDescriptor.getSDMStores());
        if (result) {  result = testSDMItems(sdmDescriptor.getSDMItems()) ;}
        if (result) {  result = testSDMCustomers(sdmDescriptor.getSDMCustomers()); }

        return result;
    }

    private boolean testSDMCustomers(SDMCustomers sdmCustomers) {
        List<SDMCustomer> customersCollection = sdmCustomers.getSDMCustomer();
        if (!checkCustomerIds(customersCollection)) { throw new DuplicateItemIdsException("There are duplicate ids for customers in file."); }

        return true;
    }

    private boolean testSDMItems(SDMItems sdmItems) {
        List<SDMItem> productsCollection = sdmItems.getSDMItem();
        if (!testSDMItemsIds(productsCollection)) { throw new DuplicateItemIdsException("There are duplicate ids for products in file."); }
        checkAllProductsAreSold(productsCollection); //Throws exception if there are issues
        return true;
    }

    private void checkAllProductsAreSold(List<SDMItem> collection) {
        List<SDMStore> stores = descriptor.getSDMStores().getSDMStore();
        Map<Integer, Boolean> copy = new TreeMap<>();
        for(SDMItem item : collection) {
            copy.put(item.getId(), false);
        }
        for (SDMItem item : collection) {
            for(SDMStore store : stores) {
                List<SDMSell> sells = store.getSDMPrices().getSDMSell();
                for(SDMSell sell : sells) {
                    copy.put(sell.getItemId(), true);
                }
            }
        }
        for (Boolean isExist : copy.values()) {
            if(!isExist) {
                throw new ProductIsNotSoldByVendorException("File failed to load. There is a product that is not sold by any store");
            }
        }
    }

    private void checkAllStoresPointToExistingProducts(List<SDMStore> storesCollection) {
        List<SDMItem> items = descriptor.getSDMItems().getSDMItem();
        for(SDMStore store : storesCollection) {
            List<SDMSell> sells = store.getSDMPrices().getSDMSell();
            for(SDMSell sell : sells) {
                int count = (int) items.stream()
                        .map(SDMItem::getId)
                        .filter(itemId -> itemId == sell.getItemId())
                        .count();
                if (count == 0) {
                    throw new ProductIsNotSoldByVendorException("File failed to load. There is a store that sells a product with a non existing Id"); }
            }
        }
    }

    private boolean testSDMStores(SDMStores sdmStores) {
        List<SDMStore> storesCollection = sdmStores.getSDMStore();
        if(!checkStoreIds(storesCollection)) { throw new DuplicateStoresIdsException("There are duplicate ids for stores in file");}
        if(!checkStoreCoordinates(storesCollection)) { throw new LocationOutOfBoundsException("Locations are not within maps range in file"); }
        checkAllStoresPointToExistingProducts(storesCollection); //Throws exception if there are issues
        checkProductIsSoldOnceInStore(storesCollection); //Throws exception if there are issues
        //checkDiscountDefinitions(storesCollection);
        return true;
    }

    //TODO finish this
    private void checkDiscountDefinitions(List<SDMStore> storesCollection) {
        SDMDiscounts discounts;

        for (SDMStore store : storesCollection) {
            discounts = store.getSDMDiscounts();
        }

    }

    private void checkProductIsSoldOnceInStore(List<SDMStore> storesCollection) {
        for(SDMStore store : storesCollection) {
            List<SDMSell> sells = store.getSDMPrices().getSDMSell();
            int idsCount = (int) sells.stream()
                    .mapToInt(SDMSell::getItemId)
                    .distinct()
                    .count();
            if (idsCount < sells.size()) {
                throw new DuplicateSoldItemInStoreException("There is an item that is registered twice in the same store");
            }
        }
    }

    private boolean checkStoreCoordinates(List<SDMStore> storesCollection) {
        int size = (int) storesCollection.stream()
                .map(SDMStore::getLocation)
                .filter(location -> location.getX() > 50 || location.getX() < 1 || location.getY() > 50 || location.getY() < 1)
                .count();
        return size == 0;
    }

    private boolean checkStoreIds(List<SDMStore> sdmStores) {
        int count = (int) sdmStores.stream()
                .map(SDMStore::getId)
                .count();
        int distinctCount = (int) sdmStores.stream()
                .map(SDMStore::getId)
                .distinct()
                .count();

        return count == distinctCount;
    }

    private boolean checkCustomerIds(List<SDMCustomer> sdmCustomers) {
        int count = (int) sdmCustomers.stream()
                .map(SDMCustomer::getId)
                .count();
        int distinctCount = (int) sdmCustomers.stream()
                .map(SDMCustomer::getId)
                .distinct()
                .count();

        return count == distinctCount;
    }

    private boolean testSDMItemsIds(List<SDMItem> collection) {
        int count = (int) collection.stream()
                .map(SDMItem::getId)
                .count();
        int distinctCount = (int) collection.stream()
                .map(SDMItem::getId)
                .distinct()
                .count();

        return count == distinctCount;
    }

    public boolean validateExistence(File file) {
        if (file.exists() && file.isFile() && getFileExtension(file).equals("xml")) {
            return true;
        } else {
            throw new FileFormatIsNotXmlException("File failed to load. Format of target is not an .xml file");
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
