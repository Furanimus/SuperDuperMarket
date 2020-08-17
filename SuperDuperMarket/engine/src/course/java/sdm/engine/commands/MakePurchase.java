package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class MakePurchase implements ICommand {

    @Override
    public String getDescription() {
        return "Make a purchase";
    }

    @Override
    public String execute(SystemManagerSingleton systemManager) {
        return "";
    }
}
