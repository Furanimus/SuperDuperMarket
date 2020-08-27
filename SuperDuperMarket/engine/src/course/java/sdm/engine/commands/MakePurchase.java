package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class MakePurchase implements MenuItem {

    @Override
    public String showMe() {
        return "Make a purchase";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        return "";
    }
}
