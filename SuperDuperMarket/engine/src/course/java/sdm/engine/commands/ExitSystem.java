package course.java.sdm.engine.commands;


import course.java.sdm.engine.SystemManagerSingleton;

public class ExitSystem implements ICommand {
    @Override
    public String getDescription() {
        return "Exit System";
    }

    @Override
    public Object execute(SystemManagerSingleton systemManager) {
        return "Thanks for using my Super Duper Market \n" +
                "Goodbye";
    }
}
