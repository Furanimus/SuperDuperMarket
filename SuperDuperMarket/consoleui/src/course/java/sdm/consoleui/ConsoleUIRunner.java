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
            if (mainMenu.readUserChoiceAndValidate()) {
                mainMenu.activateChoice();
            }
        }
    }
}