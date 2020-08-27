package course.java.sdm.consoleui;

import course.java.sdm.engine.*;
import course.java.sdm.engine.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenuImpl implements Menu {

    private int userChoice;
    private boolean isChoiceValid;
    public static List<MenuItem> mainMenu = new ArrayList<>();
    //public static List<MenuItem> menuCommands = new ArrayList<>();
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
                break;
            case 3:
                /*
                List<String> results = (ArrayList<String>) mainMenu.get(userChoice - 1).activate(systemManager);
                for (String res : results) {
                    System.out.println(res);
                }

                 */
                break;
            case 4: mainMenu.get(userChoice - 1).activate(systemManager);
                break;
            case 5:
                break;
            case 6:
                ConsoleUIRunner.isRunning = false;
            default:
                break;
        }
        System.out.println(mainMenu.get(userChoice - 1).activate(systemManager));
    }
}