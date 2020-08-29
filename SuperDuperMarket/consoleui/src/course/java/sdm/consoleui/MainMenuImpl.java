package course.java.sdm.consoleui;

import com.sun.org.apache.xpath.internal.operations.Or;
import course.java.sdm.engine.*;
import course.java.sdm.engine.commands.*;
import course.java.sdm.engine.entities.Location;
import course.java.sdm.engine.entities.Order;
import course.java.sdm.engine.entities.Product;
import course.java.sdm.engine.entities.Vendor;
import course.java.sdm.engine.exceptions.LocationOutOfBoundsException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenuImpl implements Menu {

    private int userChoice;
    private final String ESCAPE_CHAR ="q";
    private boolean isChoiceValid;
    public static List<MenuItem> mainMenu = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);
    private final SystemManagerSingleton systemManager = SystemManagerSingleton.getInstance();
    private final VendorManager vendorManager = VendorManager.getInstance();

    public MainMenuImpl() {
        initMenu();
    }

    public void initMenu() {
        mainMenu.add(new ReadFileXml());
        mainMenu.add(new ShowVendorsInfo());
        mainMenu.add(new ShowProductsInfo());
        mainMenu.add(new MakePurchase());
        mainMenu.add(new ShowPurchaseHistory());
        mainMenu.add(new ExitSystem());
    }

    @Override
    public void showMe() {
        System.out.println("Please choose your option (press the digit and then enter)");
        for (int i = 0; i < mainMenu.size(); i++) {
            System.out.println(String.format("%d) %s", i + 1, mainMenu.get(i).showMe()));
        }
    }

    @Override
    public boolean readUserChoiceAndValidate() {
            try {
                userChoice = Integer.parseInt(SCANNER.nextLine());
                return validate(userChoice);
            } catch (NumberFormatException ex) {
                System.out.println("Wrong input entered (not a number): Please enter a valid number between 1-6");
            }
            catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
        }
        return false;
    }

    private boolean validate(int choice) {
        try {
            int upperLimit = mainMenu.size();
            int lowerLimit = 1;

            if (checkIfWithinBounds(choice, lowerLimit, upperLimit)) {
                if (systemManager.getIsFileLoaded()) {
                    isChoiceValid = true;
                } else {
                    continueForNotLoadedFile(choice, lowerLimit, upperLimit);
                }
            } else {
                System.out.println("Please enter a number between 1-" + upperLimit);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong input entered (not a number): Please enter a valid number between 1-6");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return isChoiceValid;
    }

    private boolean checkIfWithinBounds(int number,int lowerBound, int upperBound) {
        return number <= upperBound && number >= lowerBound;
    }

    private void continueForNotLoadedFile(int choice, int lowerBound, int upperBound) {
        if (choice == lowerBound || choice == upperBound) {
            isChoiceValid = true;
        } else {
            System.out.println("Please load a file before running other commands");
            isChoiceValid = false;
        }
    }

    @Override
    public void activateChoice() {
        switch (userChoice) {
            case 1:
                System.out.println("Please enter file's path");
                systemManager.setFilePath(SCANNER.nextLine());
                System.out.println(mainMenu.get(userChoice - 1).activate(systemManager));
                break;
            case 2:
                ArrayList<Map<String, Object>> vendors =
                        (ArrayList<Map<String, Object>>) mainMenu.get(userChoice - 1).activate(systemManager);
                System.out.println(getVendorListString(vendors));
                break;
            case 3:ArrayList<Map<String, Object>> products =
                    (ArrayList<Map<String, Object>>) mainMenu.get(userChoice - 1).activate(systemManager);
                System.out.println(getProductsString(products));
                break;
            case 4:
                handleMakeOrder();
                break;
            case 5:

                break;
            case 6:
                ConsoleUIRunner.isRunning = false;
            default:
                break;
        }
    }

    private void handleMakeOrder() {
        int choice = showStoresAndPick();
        if (checkIfWithinBounds(choice, 1, systemManager.getVendorManager().getVendorsInfo().size())) {
            startOrder(VendorManager.getInstance().getVendorsInfo().get(choice - 1));
        } else {
            System.out.println("Please pick an number within range of available stores.");
        }
    }

    private void startOrder(Map<String, Object> vendorInfo) {
        Order order = new Order();
        int vendorId = (Integer) vendorInfo.get("Id");

        Map<Integer, Object> products;
        do {
            System.out.println("Please enter the date you wish the order will arrive (Please use the following format: " + Order.getDATE_FORMAT());
        } while(!readDate(order));
        do {
            System.out.println("Please enter the location to which the order will be sent");
        } while(!readLocation(order));

        collectOrderItems(order, vendorInfo);
        prepareOrderToFinish(order);

    }

    private void prepareOrderToFinish(Order order) {

    }

    private void collectOrderItems(Order order, Map<String, Object> vendor) {
        String input;
        Map<String, Object> productsInfo = (Map<String, Object>)vendor.get("Products");
        Map<Integer, Product> idToProduct = systemManager.getProductsMap();
        do {
            System.out.println(getItemsString(productsInfo)); //TODO remove the number of times....
            System.out.println("Please enter the item ID you would like to add to the cart. Enter q to wrap up the order.");
            input = SCANNER.nextLine();
            if(!input.equals(ESCAPE_CHAR)) {
                try {
                    int id = Integer.parseInt(input);
                    if(idToProduct.containsKey(id)) {
                        order.addProduct(idToProduct.get(id));
                    } else {
                        System.out.println("The entered product id is not available in this store");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number that represents a product id");
                }
            }
        } while (!input.equals(ESCAPE_CHAR));
    }

    private boolean readLocation(Order order) {
        boolean result = false;
        try {
            System.out.println("Please enter coordinate X");
            int x = Integer.parseInt(SCANNER.nextLine());
            System.out.println("Please enter coordinate Y");
            int y = Integer.parseInt(SCANNER.nextLine());
            Location target = new Location(x, y);
            if(!systemManager.getVendorManager().isLocationExist(target)) {
                order.setTargetLocation(target);
                result = true;
            } else {
                System.out.println("There is a store registered in this location");
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input: Please enter a valid number between 1-50 for coordinate " );
        } catch (LocationOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private boolean readDate(Order order) {
        boolean isValid = false;
        while (!isValid) {
            try {
                if(order.setDate(SCANNER.nextLine())) {
                    isValid = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\nPlease enter a valid number between 1-50 for coordinate ");
            }
        }
        return isValid;
    }

    private String getProductsString(ArrayList<Map<String, Object>> products) {
        StringBuilder sb = new StringBuilder();
        for(Map<String, Object> item : products) {
            sb.append("Product name: ");
            sb.append(item.get("Name"));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("Product Id: ");
            sb.append(item.get("Id"));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("Purchase Category: ");
            sb.append(item.get("Purchase Category"));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("Number of stores that sell the product: ");
            sb.append(item.get("Number Of Stores that Sell the product"));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("Product Average Price: ");
            sb.append(item.get("Product Average Price"));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getVendorListString(List<Map<String, Object>> vendors) {
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> product : vendors) {
            sb.append("Store name: ");
            sb.append(product.get("Name"));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("Store Id: ");
            sb.append(product.get("Id"));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("PPK: ");
            sb.append(product.get("PPK"));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("Store products: ");
            sb.append(getItemsString((Map<String, Object>)product.get("Products")));//TODO AAA
        }
        return sb.toString();
    }

    private String getItemsString(Map<String, Object> store_products) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

       for (Object obj : store_products.values()) {
           Map<String, Object> currentProduct = (Map<String, Object>) obj;
           sb.append("Name:");
           sb.append(currentProduct.get("Name"));
           sb.append(ConsoleUIRunner.SEPARATOR);
           sb.append("Id:");
           sb.append(currentProduct.get("Id"));
           sb.append(ConsoleUIRunner.SEPARATOR);
           sb.append("Purchase Category:");
           sb.append(currentProduct.get("Purchase Category"));
           sb.append(ConsoleUIRunner.SEPARATOR);
           sb.append("Number of times the product was sold from this store:");
           sb.append(currentProduct.get("Number of times the product was sold from this store")); //TODO add a function or something
           sb.append(ConsoleUIRunner.SEPARATOR);
           sb.append("Price:");
           sb.append(currentProduct.get("Price"));
           sb.append("\n");
           //sb.append(obj.toString());
        }
        sb.append("}\n");
        return sb.toString();
    }

    private int showStoresAndPick() {
        int index = 1;
        VendorManager vendorManager = VendorManager.getInstance();
        ArrayList<Map<String, Object>> vendors = vendorManager.getVendorsInfo();
        System.out.println("Please choose the store you would like to make a purchase from:");
        for (Map<String, Object> vendor : vendors) {
            System.out.println(String.format("%d) %s | ID: %d | PPK: %d",
                                             index++, vendor.get("Name"),
                                             vendor.get("Id"),
                                             vendor.get("PPK")));
        }
        try {
            index = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer number");
        }
        return index;
    }
}