package course.java.sdm.consoleui;

import course.java.sdm.engine.*;
import course.java.sdm.engine.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainMenuImpl implements Menu {

    private int userChoice;
    private boolean isChoiceValid;
    public static List<MenuItem> mainMenu = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);
    private final SystemManagerSingleton systemManager = SystemManagerSingleton.getInstance();

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
                mainMenu.get(userChoice - 1).activate(systemManager);
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
        //System.out.println(mainMenu.get(userChoice - 1).activate(systemManager));
    }

    private void handleMakeOrder() {
        int choice = showStoresAndPick();
        if (checkIfWithinBounds(choice, 1, systemManager.getVendorManager().getVendorsInfo().size())) {

        } else {
            System.out.println("Please pick an number within range of available stores.");
        }
    }

    private String getProductsString(ArrayList<Map<String, Object>> products) {
        StringBuilder sb = new StringBuilder();
        for(Map<String, Object> item : products) {
            sb.append("Product name: ");
            sb.append(item.get("Name"));
            sb.append(systemManager.SEPARATOR);
            sb.append("Product Id: ");
            sb.append(item.get("Id"));
            sb.append(systemManager.SEPARATOR);
            sb.append("Purchase Category: ");
            sb.append(item.get("Purchase Category"));
            sb.append(systemManager.SEPARATOR);
            sb.append("Number of stores that sell the product: ");
            sb.append(item.get("Number Of Stores that Sell the product"));
            sb.append(systemManager.SEPARATOR);
            sb.append("Product Average Price: ");
            sb.append(item.get("Product Average Price"));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getVendorListString(List<Map<String, Object>> list) {
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> item : list) {
/*            for(String key : item.keySet()) {
                sb.append(key);
                sb.append(": ");
                sb.append(item.get(key));
                sb.append(systemManager.SEPARATOR);
            }
            System.out.println(sb.toString());
            sb.delete(0, sb.length());
 */
            sb.append("Store name: ");
            sb.append(item.get("Name"));
            sb.append(systemManager.SEPARATOR);
            sb.append("Store Id: ");
            sb.append(item.get("Id"));
            sb.append(systemManager.SEPARATOR);
            sb.append("Store products: ");
            sb.append("PPK: ");
            sb.append(item.get("PPK"));
            sb.append(systemManager.SEPARATOR);
            sb.append(getItemsString((Map<String, Object>)item.get("Store products")));//TODO AAA
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
           sb.append(systemManager.SEPARATOR);
           sb.append("Id:");
           sb.append(currentProduct.get("Id"));
           sb.append(systemManager.SEPARATOR);
           sb.append("Purchase Category:");
           sb.append(currentProduct.get("Purchase Category"));
           sb.append(systemManager.SEPARATOR);
           sb.append("Number of times the product was sold from this store:");
           sb.append(currentProduct.get("Number of times the product was sold from this store")); //TODO add a function or something
           sb.append(systemManager.SEPARATOR);
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
        VendorManager vendorManager = systemManager.getVendorManager();
        ArrayList<Map<String, Object>> vendors = vendorManager.getVendorsInfo();
        System.out.println("Please choose the store you would like to make a purchase from:");
        for (Map<String, Object> vendor : vendors) {
            System.out.println(String.format("%d) %s | ID: %d | PPK: %d",
                                             index++, vendor.get("Name"),
                                             vendor.get("Id"),
                                             vendor.get("PPK")));
        }
        return Integer.parseInt(SCANNER.nextLine());
    }
}