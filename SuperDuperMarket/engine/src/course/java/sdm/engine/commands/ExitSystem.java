package course.java.sdm.engine.commands;


import course.java.sdm.engine.SystemManagerSingleton;

public class ExitSystem implements MenuItem {
    @Override
    public String showMe() {
        return "Exit System";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        return "Thanks for using my Super Duper Market \n" +
                "Goodbye";
    }
}
