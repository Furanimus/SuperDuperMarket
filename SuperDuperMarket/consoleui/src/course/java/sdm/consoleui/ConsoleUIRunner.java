package course.java.sdm.consoleui;

import course.java.sdm.engine.SystemManagerSingleton;
import course.java.sdm.engine.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUIRunner {
    public static List<ICommand> menuCommands = new ArrayList<>();
    public static SystemManagerSingleton systemManager;
    public static boolean isRunning = true;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        initComponents();

        while (isRunning) {
            printMenu();
            getAndValidateChoice();
        }
        System.out.println();
    }

    private static void initComponents() {
        systemManager = SystemManagerSingleton.getInstance();
        initICommandList();
    }

    private static void getAndValidateChoice() {
        int choice;
        int upperLimit = menuCommands.size();
        int lowerLimit = 1;
        try {
            choice = Integer.parseInt(SCANNER.nextLine());
            if (checkIfWithinBounds(choice, lowerLimit, upperLimit)) {
                if (systemManager.getIsFileLoaded()) {
                    handleInput(choice);
                } else {
                    continueForNotLoadedFile(choice, lowerLimit, upperLimit);
                }
            } else {
                System.out.println("Please enter a number between 1-" + upperLimit);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong input entered (not a number): Please enter a valid number between 1-6");
        }
    }

    private static void continueForNotLoadedFile(int choice, int lowerBound, int upperBound) {
        if (choice == lowerBound || choice == upperBound) {
            handleInput(choice);
            systemManager.fileLoaded();
        } else {
            System.out.println("Please load a file before running other commands");
        }
    }

    private static boolean checkIfWithinBounds(int number,int lowerBound, int upperBound) {
        return number <= upperBound && number >= lowerBound;
    }

    private static void handleInput(int input) {
        switch (input) {
            case 1:
                System.out.println("Please enter file's path");
                systemManager.setFilePath(SCANNER.nextLine());
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                isRunning = false;
            default:
                break;
        }
        System.out.println(menuCommands.get(input - 1).execute(systemManager));
    }

    private static void printMenu() {
        System.out.println("Please choose your option (press the digit and then enter)");
        for (int i = 0; i < menuCommands.size(); i++) {
            System.out.println(String.format("%d) %s", i + 1, menuCommands.get(i).getDescription()));
        }
    }

    private static void initICommandList() {
        menuCommands.add(new ReadFileXml());
        menuCommands.add(new ShowVendorsInfo());
        menuCommands.add(new ShowProductsInfo());
        menuCommands.add(new MakePurchase());
        menuCommands.add(new ShowPurchaseHistory());
        menuCommands.add(new ExitSystem());
    }
}