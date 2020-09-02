package course.java.sdm.consoleui;

public class ConsoleUIRunner {
    public static final String SEPARATOR = " | ";
    public static boolean isRunning = true;
    private static Menu mainMenu = new MainMenuImpl();

    public static void main(String[] args) {
        while (isRunning) {
            mainMenu.showMe();
            if (mainMenu.readUserChoiceAndValidate()) {
                mainMenu.activateChoice();
            }
        }
    }
}