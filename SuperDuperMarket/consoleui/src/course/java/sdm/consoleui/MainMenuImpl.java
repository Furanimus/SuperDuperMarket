package course.java.sdm.consoleui;

import course.java.sdm.engine.commands.*;
import course.java.sdm.engine.entities.*;
import course.java.sdm.engine.exceptions.LocationOutOfBoundsException;
import course.java.sdm.engine.managers.*;
import course.java.sdm.engine.utils.MyUtils;

import java.util.*;

public class MainMenuImpl implements Menu {
    private int userChoice;
    private final String ESCAPE_CHAR ="q";
    private boolean isChoiceValid;
    public static List<MenuItem> mainMenu = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);
    private final EngineManagerSingleton engineManager = EngineManagerSingleton.getInstance();
    private final StoreManagerSingleton vendorManager = StoreManagerSingleton.getInstance();

    public MainMenuImpl() {
        initMenu();
    }

    public void initMenu() {
        mainMenu.add(new ReadFileXml());
        mainMenu.add(new ShowStoreInfo());
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
                System.out.println("Wrong input entered (not an integer): Please enter a valid integer between 1-6");
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
                if (engineManager.getIsFileLoaded()) {
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
                engineManager.setFilePath(SCANNER.nextLine());
                System.out.println(mainMenu.get(userChoice - 1).execute(engineManager));
                break;
            case 2:
                ArrayList<Map<String, Object>> vendors =
                        (ArrayList<Map<String, Object>>) mainMenu.get(userChoice - 1).execute(engineManager);
                System.out.println(getVendorListString(vendors));
                break;
            case 3:ArrayList<Map<String, Object>> products =
                    (ArrayList<Map<String, Object>>) mainMenu.get(userChoice - 1).execute(engineManager);
                System.out.println(getProductsString(products));
                break;
            case 4:
                handleMakeOrder();
                break;
            case 5:
                System.out.println(mainMenu.get(userChoice - 1).execute(engineManager));
                break;
            case 6:
                ConsoleUIRunner.isRunning = false;
                System.out.println(mainMenu.get(userChoice - 1).execute(engineManager));
            default:
                break;
        }
    }

    private void handleMakeOrder() {
        int choice = showStoresAndPick();
        if (checkIfWithinBounds(choice, 1, engineManager.getVendorManager().getVendorsInfo().size())) {
            startOrder(vendorManager.getVendorsInfo().get(choice - 1));
        } else {
            System.out.println("Please pick an number within range of available stores.");
        }
    }

    private void startOrder(Map<String, Object> vendorInfo) {
        Store store = vendorManager.getVendor((int) vendorInfo.get("Id"));
        if (store != null) {
            Order order = new Order(store);
            do {
                System.out.println("Please enter the date you wish the order will arrive (Please use the following format: " + Order.getDATE_FORMAT());
            } while(!readDate(order));
            do {
                System.out.println("The system will now read the location to which the order will be sent on a map of 50 X 50 in the following format (x, y)");
            } while(!readLocation(order));
            collectOrderItems(order, store);
            prepareOrderToFinish(order);
        } else {
            System.out.println("Store does not exist");
        }
    }

    private void prepareOrderToFinish(Order order) {
        if (order.getDifferentProductCount() == 0) {
            System.out.println("The order is empty.");
        } else {
            System.out.println("Please find the order details below:");
            System.out.println(order.toString());
            System.out.println("Do you wish to proceed with the order? Enter 'y' to save the order or 'n' to drop it");
            String str = "";

            while (!str.equals("y") && !str.equals(("n"))) {
                str = SCANNER.nextLine();
                if (str.equals("y")) {
                    engineManager.getOrderManager().addOrder(order);
                    System.out.println("Your order has been registered in the system");
                } else if (str.equals("n")) {
                    System.out.println("Your order has been dropped");
                } else {
                    System.out.println("Please enter 'y' to save the order or 'n' to drop it");
                }
            }
        }
    }

    private void collectOrderItems(Order order, Store store) {
        String input;
        Map<Integer, Product> products = store.getProductsMap();
        do {
            printProducts(products);
            System.out.println("Please enter the item ID you would like to add to the cart. If you are done with the order, press q to finish.");
            input = SCANNER.nextLine();
            if(!input.equals(ESCAPE_CHAR)) {
                try {
                    int id = Integer.parseInt(input);
                    if(products.containsKey(id)) {
                        Product product = engineManager.getProduct(id);

                        System.out.println("Choose the amount of the requested product: For quantity specify number of units and for Weight specify the weight in kilos");
                        double amount = readAmountFromUser(product);
                        order.addProduct(id, amount);
                    } else {
                        System.out.println("Product is not sold in this store");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number that represents a product id");
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!input.equals(ESCAPE_CHAR));
    }

    private void printProducts(Map<Integer, Product> products) {
        for(Product product : products.values()) {
            System.out.println(product);
        }
    }

    private double readAmountFromUser(Product product) {
        double amount = Double.parseDouble(SCANNER.nextLine());;
        if (product.getPurchaseCategory().equals("Quantity")) {
            if (amount % 1 != 0) {
                throw new InputMismatchException("Purchase category for prouduct is Quantity.\nPlease enter a Whole number");
            }
        }
        return amount;
    }

    private boolean readLocation(Order order) {
        boolean result = false;
        try {
            System.out.println("Please enter coordinate X (Value between 1-50)");
            int x = Integer.parseInt(SCANNER.nextLine());
            System.out.println("Please enter coordinate Y (Value between 1-50)");
            int y = Integer.parseInt(SCANNER.nextLine());
            Location target = new Location(x, y);
            if(!engineManager.getVendorManager().isLocationExist(target)) {
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
                System.out.println(e.getMessage() + "\nPlease enter a valid date as per format" + Order.getDATE_FORMAT());
            }
        }
        return isValid;
    }

    private String getProductsString(ArrayList<Map<String, Object>> products) {
        StringBuilder sb = new StringBuilder();
        for(Map<String, Object> item : products) {
            sb.append("Product name: "); //TODO MUCH LATER - fix in order to not duplicate code
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
            sb.append(MyUtils.formatNumber((double)item.get("Product Average Price")));
            sb.append(ConsoleUIRunner.SEPARATOR);
            sb.append("Product was sold: ");
            sb.append(item.get("Times Sold"));
            sb.append(" times");
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getVendorListString(List<Map<String, Object>> vendors) {
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> vendor : vendors) {
            sb.append("Store name: ");
            sb.append(vendor.get("Name"));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Store Id: ");
            sb.append(vendor.get("Id"));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("PPK: ");
            sb.append(vendor.get("PPK"));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Store products: ");
            sb.append(getItemsString((Map<Integer, Object>)vendor.get("Products")));//TODO AAA
            sb.append("Orders made from this store: ");
            List<Order> orders = OrderManagerSingleton.getInstance().getOrdersByVendorId((int)vendor.get("Id"));
            sb.append(OrderManagerSingleton.getInstance().getOrderInfoString(orders));
            sb.append("Total products that were sold from this store: ");
            sb.append(vendor.get("Total Sold Products"));
            sb.append(MyUtils.STRING_SEPARATOR);
            sb.append("Summed Delivery Price: ");
            sb.append(vendor.get("Summed Delivery Price"));
            sb.append("\n\n");
        }
        return sb.toString();
    }

    private String getItemsString(Map<Integer, Object> store_products) {
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
           sb.append(currentProduct.get("Time Sold"));
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
        StoreManagerSingleton vendorManager = StoreManagerSingleton.getInstance();
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