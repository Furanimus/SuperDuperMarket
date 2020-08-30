package course.java.sdm.engine.commands;

import course.java.sdm.engine.managers.SystemManagerSingleton;

public class ShowPurchaseHistory implements MenuItem {
    @Override
    public String showMe() {
        return "View Purchase history so far";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        System.out.println("In ShowPurchaseHistory");
        return "Not Implemented yet";
    }
}
