package course.java.sdm.engine.xml;

import course.java.sdm.engine.SystemManagerSingleton;
import course.java.sdm.engine.VendorManager;
import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.xml.jaxbobjects.SDMItem;
import course.java.sdm.engine.xml.jaxbobjects.SDMStore;
import course.java.sdm.engine.xml.jaxbobjects.SuperDuperMarketDescriptor;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JAXBObjectsToMyObjects {

    public final String WEIGHT_CATEGORY = "Weight";

    private final String JAXB_XML_PACKAGE_NAME = "course.java.sdm.engine.xml.jaxbobjects";
    private  String xmlPath;
    private  SystemManagerSingleton systemManagerInstance;

    public JAXBObjectsToMyObjects() {
        this.systemManagerInstance = SystemManagerSingleton.getInstance();
    }

    public void jaxbObjectToMyObject() throws FileNotFoundException, JAXBException {
        xmlPath = systemManagerInstance.getFilePath();
        //xmlPath = "C:\\git\\SuperDuperMarket\\SuperDuperMarket\\ex1-big.xml";
        InputStream inputStream = new FileInputStream(new File(xmlPath));

        try {
            SuperDuperMarketDescriptor sdmDescriptor = deserializeFrom(inputStream);
            copyFromJAXB(systemManagerInstance, sdmDescriptor);
        } catch (Exception e) {
            System.out.println("failed to deserialize SDMDescriptor");
        }
    }

    private void copyFromJAXB(SystemManagerSingleton systemManagerInstance, SuperDuperMarketDescriptor descriptor) {
        List<SDMItem> readItems = descriptor.getSDMItems().getSDMItem();
        List<SDMStore> readStores = descriptor.getSDMStores().getSDMStore();
        copyItems(systemManagerInstance.getProductsMap(), readItems);
        copyStores(systemManagerInstance.getVendorManager().getIdToVendor(), readStores);
    }

    private void copyStores(Map<Integer, Vendor> vendorsMap, List<SDMStore> storeList) {
        for(SDMStore store : storeList) {
            Location newLocation = new Location(store.getLocation().getX(),
                    store.getLocation().getY());
            vendorsMap.put(store.getId(), new Vendor(store.getId(),
                    store.getName(),
                    store.getDeliveryPpk(),
                    newLocation));
        }
    }

    private void copyItems(Map<Integer, Product> productsMap, List<SDMItem> itemList) {
        for(SDMItem item : itemList) {
            productsMap.put(item.getId(), new Product(item.getId(),
                    item.getName(),
                    item.getPurchaseCategory().equals(WEIGHT_CATEGORY)));
        }
    }

    private SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
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