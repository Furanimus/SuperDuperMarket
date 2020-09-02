package course.java.sdm.engine.xml;

import course.java.sdm.engine.managers.EngineManagerSingleton;
import course.java.sdm.engine.managers.VendorManagerSingleton;
import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.xml.jaxbobjects.SDMItem;
import course.java.sdm.engine.xml.jaxbobjects.SDMSell;
import course.java.sdm.engine.xml.jaxbobjects.SDMStore;
import course.java.sdm.engine.xml.jaxbobjects.SuperDuperMarketDescriptor;

import javax.xml.bind.*;
import java.io.*;
import java.util.List;
import java.util.Map;

public class ObjectsDecoder {

    public final String WEIGHT_CATEGORY = "Weight";
    FileValidator fileValidator = new FileValidator();
    private final String JAXB_XML_PACKAGE_NAME = "course.java.sdm.engine.xml.jaxbobjects";
    private  String xmlPath;
    private EngineManagerSingleton systemManagerInstance;
    private VendorManagerSingleton vendorManager;

    public ObjectsDecoder() {
        this.systemManagerInstance = EngineManagerSingleton.getInstance();
        vendorManager = VendorManagerSingleton.getInstance();
    }

    public void jaxbObjectToMyObject(SuperDuperMarketDescriptor sdmDescriptor) {
        xmlPath = systemManagerInstance.getFilePath();
        File file = new File(xmlPath);
        try {
            copyFromJAXB(systemManagerInstance, sdmDescriptor);
        } catch (Exception e) {
            System.out.println("failed to copy SDMDescriptor"); //TODO fix to be more flexible
        }
    }

    private void copyFromJAXB(EngineManagerSingleton systemManagerInstance, SuperDuperMarketDescriptor descriptor) {
        List<SDMItem> readItems = descriptor.getSDMItems().getSDMItem();
        List<SDMStore> readStores = descriptor.getSDMStores().getSDMStore();
        systemManagerInstance.resetData();
        copyItems(systemManagerInstance.getProductsMap(), readItems);
        copyStores(readStores);
    }

    private void copyStores(List<SDMStore> storeList) {
        Map<Integer,Product> productMap = systemManagerInstance.getProductsMap();
        for(SDMStore store : storeList) {
            Location newLocation = new Location(store.getLocation().getX(), store.getLocation().getY());
            Vendor vendorToAdd = new Vendor(store.getId(), store.getName(), store.getDeliveryPpk(), newLocation);
            for(SDMSell sdmSell : store.getSDMPrices().getSDMSell()) {
                vendorToAdd.addProduct(productMap.get(sdmSell.getItemId()), sdmSell.getPrice());
            }
            vendorManager.addVendor(vendorToAdd);
        }
    }

    private void copyItems(Map<Integer, Product> productsMap, List<SDMItem> itemList) {
        for(SDMItem item : itemList) {
            productsMap.put(item.getId(), new Product(item.getId(),
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