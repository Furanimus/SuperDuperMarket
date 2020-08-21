package course.java.sdm.engine.xml;

import course.java.sdm.engine.SystemManagerSingleton;
import course.java.sdm.engine.VendorManager;
import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.xml.jaxbobjects.SDMItem;
import course.java.sdm.engine.xml.jaxbobjects.SDMStore;
import course.java.sdm.engine.xml.jaxbobjects.SuperDuperMarketDescriptor;
import javafx.animation.ScaleTransition;

import javax.xml.bind.*;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JAXBObjectsToMyObjects {

    private final String JAXB_OBJECTS_PACKAGE = "course.java.sdm.engine.xml.jaxbobjects";
    private final String XML_PATH = SystemManagerSingleton.getInstance().getFilePath();
    private  SystemManagerSingleton systemManagerInstance = SystemManagerSingleton.getInstance();
    public JAXBObjectsToMyObjects() {
    }

    public void JAXBObjectToMyObject() {
        InputStream inputStream = JAXBObjectsToMyObjects.class.getResourceAsStream(XML_PATH);
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
        copyStores(systemManagerInstance.getVendorManager(), readStores);
    }

    private void copyStores(VendorManager vendorManager, List<SDMStore> storeList) {
        Map<Integer, Vendor> map = vendorManager.getIdToVendor();

        for(SDMStore store : storeList) {
            Location newLocation = new Location(store.getLocation().getX(),
                    store.getLocation().getY());
            if(map.containsKey(newLocation)) {
                //throw StoreLocationAlreadyExistException TODO
            } else {
                //moveToMap();
            }
        }
    }

    private void copyItems(Map<Integer, Product> productsMap, List<SDMItem> itemList) {

    }

    private SuperDuperMarketDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_OBJECTS_PACKAGE);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(inputStream);
    }

    //Use streams somehow
    public boolean validateXmlInformation() {
        boolean isValid = true;


        return isValid;
    }
}