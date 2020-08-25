package course.java.sdm.consoleui;

import course.java.sdm.engine.SystemManagerSingleton;
import course.java.sdm.engine.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenuImpl implements Menu {

    private int userChoice;
    private boolean isChoiceValid;
    public static List<ICommand> menuCommands = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);
    private final SystemManagerSingleton systemManager = SystemManagerSingleton.getInstance();

    public MainMenuImpl() {
        initMenu();
    }

    public void initMenu() {
        menuCommands.add(new ReadFileXml());
        menuCommands.add(new ShowVendorsInfo());
        menuCommands.add(new ShowProductsInfo());
        menuCommands.add(new MakePurchase());
        menuCommands.add(new ShowPurchaseHistory());
        menuCommands.add(new ExitSystem());
    }

    @Override
    public void showMe() {
        System.out.println("Please choose your option (press the digit and then enter)");
        for (int i = 0; i < menuCommands.size(); i++) {
            System.out.println(String.format("%d) %s", i + 1, menuCommands.get(i).getDescription()));
        }
    }

    @Override
    public void readUserChoiceAndValidate() {
            try {
                userChoice = Integer.parseInt(SCANNER.nextLine());
                validate(userChoice);
              } catch (NumberFormatException ex) {
                System.out.println("Wrong input entered (not a number): Please enter a valid number between 1-6");
            }
            catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void validate(int choice) {
        try {
            int upperLimit = menuCommands.size();
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
    }
    private boolean checkIfWithinBounds(int number,int lowerBound, int upperBound) {
        return number <= upperBound && number >= lowerBound;
    }

    private void continueForNotLoadedFile(int choice, int lowerBound, int upperBound) {
        if (choice == lowerBound || choice == upperBound) {
            isChoiceValid = true;
        } else {
            System.out.println("Please load a file before running other commands");
        }
    }

    @Override
    public void activateChoice() {
        switch (userChoice) {
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
                ConsoleUIRunner.isRunning = false;
            default:
                break;
        }
        System.out.println(menuCommands.get(userChoice - 1).execute(systemManager));
    }
}
