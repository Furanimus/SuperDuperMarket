package course.java.sdm.engine.xml;

import course.java.sdm.engine.managers.SystemManagerSingleton;
import course.java.sdm.engine.managers.VendorManagerSingleton;
import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.xml.jaxbobjects.SDMItem;
import course.java.sdm.engine.xml.jaxbobjects.SDMSell;
import course.java.sdm.engine.xml.jaxbobjects.SDMStore;
import course.java.sdm.engine.xml.jaxbobjects.SuperDuperMarketDescriptor;
import sun.reflect.generics.tree.Tree;

import javax.xml.bind.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ObjectsDecoder {

    public final String WEIGHT_CATEGORY = "Weight";

    private final String JAXB_XML_PACKAGE_NAME = "course.java.sdm.engine.xml.jaxbobjects";
    private  String xmlPath;
    private  SystemManagerSingleton systemManagerInstance;
    private VendorManagerSingleton vendorManager;

    public ObjectsDecoder() {
        this.systemManagerInstance = SystemManagerSingleton.getInstance();
        vendorManager = VendorManagerSingleton.getInstance();
    }

    //TODO check why second file fails to deserialize
    public void jaxbObjectToMyObject() throws IOException, JAXBException {
        xmlPath = systemManagerInstance.getFilePath();
        try (InputStream inputStream = new FileInputStream(new File(xmlPath))) {
            SuperDuperMarketDescriptor sdmDescriptor = deserializeFrom(inputStream);
            copyFromJAXB(systemManagerInstance, sdmDescriptor);
        } catch (IOException e) {
            System.out.println("IOException SDMDescriptor"); //TODO fix to be more flexible
        } catch (JAXBException e) {
        System.out.println("JAXBException SDMDescriptor"); //TODO fix to be more flexible
        } catch (Exception e) {
            System.out.println("failed to deserialize SDMDescriptor"); //TODO fix to be more flexible
        }
    }

    private void copyFromJAXB(SystemManagerSingleton systemManagerInstance, SuperDuperMarketDescriptor descriptor) {
        List<SDMItem> readItems = descriptor.getSDMItems().getSDMItem();
        List<SDMStore> readStores = descriptor.getSDMStores().getSDMStore();
        systemManagerInstance.resetData();
        copyItems(systemManagerInstance.getProductsMap(), readItems);
        copyStores(readStores);
    }

    private void copyStores(List<SDMStore> storeList) {
        Map<Integer,Product> productMap = systemManagerInstance.getProductsMap();
        //Map<Integer,Product> newMap = new TreeMap<>();
        //systemManager <- setMap(newMap)
        for(SDMStore store : storeList) {
            Location newLocation = new Location(store.getLocation().getX(), store.getLocation().getY());
            Vendor vendorToAdd = new Vendor(store.getId(), store.getName(), store.getDeliveryPpk(), newLocation);
            //Map<Integer, Integer> prices = vendorsMap.get(store.getId()).getIdToPrice();
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