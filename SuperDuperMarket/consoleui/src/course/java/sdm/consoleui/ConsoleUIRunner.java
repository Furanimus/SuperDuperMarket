package course.java.sdm.consoleui;

import course.java.sdm.engine.SystemManagerSingleton;

import java.awt.*;

public class ConsoleUIRunner {
    public final static SystemManagerSingleton systemManager = SystemManagerSingleton.getInstance();;
    public static boolean isRunning = true;
    private static Menu mainMenu = new MainMenuImpl();

    public static void main(String[] args) {
        //initComponents();

        while (isRunning) {
            mainMenu.showMe();
            mainMenu.readUserChoiceAndValidate();
            mainMenu.activateChoice();
        }
    }

    //private static void initComponents() {
    //    initICommandList();
    //}



//    private static void continueForNotLoadedFile(int choice, int lowerBound, int upperBound) {
//        if (choice == lowerBound || choice == upperBound) {
//            handleInput(choice);
//            systemManager.fileLoaded();
//        } else {
//            System.out.println("Please load a file before running other commands");
//        }
//    }

//    private static boolean checkIfWithinBounds(int number,int lowerBound, int upperBound) {
//        return number <= upperBound && number >= lowerBound;
//    }

//    private static void handleInput(int input) {
//        switch (input) {
//            case 1:
//                System.out.println("Please enter file's path");
//                systemManager.setFilePath(SCANNER.nextLine());
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//            case 4:
//                break;
//            case 5:
//                break;
//            case 6:
//                isRunning = false;
//            default:
//                break;
//        }
//        System.out.println(menuCommands.get(input - 1).execute(systemManager));
//    }
}