package course.java.sdm.engine.xml;

import course.java.sdm.engine.entities.*;
import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.managers.CustomerManagerSingleton;
import course.java.sdm.engine.managers.EngineManagerSingleton;
import course.java.sdm.engine.managers.StoreManagerSingleton;
import course.java.sdm.engine.xml.jaxbobjects.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ObjectsDecoder {

    public final String WEIGHT_CATEGORY = "Weight";
    FileValidator fileValidator = new FileValidator();
    private final String JAXB_XML_PACKAGE_NAME = "course.java.sdm.engine.xml.jaxbobjects";
    private  String xmlPath;
    private EngineManagerSingleton systemManagerInstance;
    private StoreManagerSingleton vendorManager;
    private CustomerManagerSingleton customerManager;

    public ObjectsDecoder() {
        this.systemManagerInstance = EngineManagerSingleton.getInstance();
        vendorManager = StoreManagerSingleton.getInstance();
        customerManager = CustomerManagerSingleton.getInstance();
    }

    public void jaxbObjectToMyObject(SuperDuperMarketDescriptor sdmDescriptor) {
        xmlPath = systemManagerInstance.getFilePath();
        copyFromJAXB(systemManagerInstance, sdmDescriptor);
    }

    private void copyFromJAXB(EngineManagerSingleton systemManagerInstance, SuperDuperMarketDescriptor descriptor) {
        List<SDMItem> readItems = descriptor.getSDMItems().getSDMItem();
        List<SDMStore> readStores = descriptor.getSDMStores().getSDMStore();
        List<SDMCustomer> readCustomers = descriptor.getSDMCustomers().getSDMCustomer();
        systemManagerInstance.resetData();
        copyItems(systemManagerInstance.getProductsMap(), readItems);
        copyStores(readStores);
        copyCustomers(readCustomers);
    }

    private void copyCustomers(List<SDMCustomer> readCustomers) {
        Map<Integer, Customer> newIdToCustomer = new TreeMap<>();
        for (SDMCustomer customer : readCustomers) {
            Location newLocation = new Location(customer.getLocation().getX(), customer.getLocation().getY());
            Customer customerToAdd = new Customer(customer.getId(), customer.getName(), newLocation);
            newIdToCustomer.put(customer.getId(), customerToAdd);
            //customerManager.addCustomer(customerToAdd);
        }
        customerManager.setIdToCustomer(newIdToCustomer);

    }

    private void copyStores(List<SDMStore> storeList) {
        Map<Integer,SmartProduct> productMap = systemManagerInstance.getProductsMap();
        for(SDMStore store : storeList) {
            Location newLocation = new Location(store.getLocation().getX(), store.getLocation().getY());
            Store storeToAdd = new Store(store.getId(), store.getName(), store.getDeliveryPpk(), newLocation);
            for(SDMSell sdmSell : store.getSDMPrices().getSDMSell()) {
                storeToAdd.addProduct(productMap.get(sdmSell.getItemId()), sdmSell.getPrice());
            }
            copyStoreSales(storeToAdd, store);
            vendorManager.addVendor(storeToAdd);
        }
    }

    private void copyStoreSales(Store storeToAdd, SDMStore source) {
        if (source.getSDMDiscounts() != null) {
            List<SDMDiscount> discounts = source.getSDMDiscounts().getSDMDiscount();
            if (discounts != null) {
                for (SDMDiscount discount : discounts) {
                    Sale newSale = createSaleType(discount.getThenYouGet().getOperator());
                    newSale.setProductYouNeedToAmountYouNeed(discount.getIfYouBuy().getItemId(), discount.getIfYouBuy().getQuantity());
                    List<SDMOffer> offers = discount.getThenYouGet().getSDMOffer();
                    for(SDMOffer offer : offers) {
                        newSale.addItemToSale(offer.getItemId(), offer.getQuantity(), offer.getForAdditional());
                    }
                    storeToAdd.addSale(newSale);
                }
            }
        }
    }

    private Sale createSaleType(String operator) {
        Sale sale;
        if(operator.equals("ALL-OR-NOTHING")) {
            sale = new AllOfSale();
        } else {
            sale = new OneOfSale();
        }
        return sale;
    }

    private void copyItems(Map<Integer, SmartProduct> productsMap, List<SDMItem> itemList) {
        for(SDMItem item : itemList) {
            productsMap.put(item.getId(), new SmartProduct(item.getId(),
                    item.getName(),
                    item.getPurchaseCategory()));
        }
    }

    public SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }

    //Use streams somehow
    public boolean validateXmlInformation() {
        boolean isValid = true;

        return isValid;
    }
}