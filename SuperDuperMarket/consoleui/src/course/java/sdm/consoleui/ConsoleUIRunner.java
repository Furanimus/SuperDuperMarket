package course.java.sdm.consoleui;
import course.java.sdm.engine.managers.SystemManagerSingleton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConsoleUIRunner {
    public final static SystemManagerSingleton systemManager = SystemManagerSingleton.getInstance();;
    public static final String SEPARATOR = " | ";
    public static boolean isRunning = true;
    private static Menu mainMenu = new MainMenuImpl();


    private void dateTimeTry() throws ParseException {
        DateFormat df = new SimpleDateFormat("dd/mm-hh:mm");
        df.parse("14/06 14:20");
        //System.out.println(df.format());

    }

    public static void main(String[] args) {
        while (isRunning) {
            mainMenu.showMe();
            if (mainMenu.readUserChoiceAndValidate()) {
                mainMenu.activateChoice();
            }
        }
    }
}