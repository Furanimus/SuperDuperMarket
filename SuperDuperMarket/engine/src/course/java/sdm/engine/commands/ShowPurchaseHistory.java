package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ShowPurchaseHistory implements ICommand {
    @Override
    public String getDescription() {
        return "View Purchase history so far";
    }

    @Override
    public String execute(SystemManagerSingleton systemManager) {
        System.out.println("In ShowPurchaseHistory");
        return "Not Implemented yet";
    }
}
