package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ShowProductsInfo implements MenuItem {
    @Override
    public String showMe() {
        return "View all available products";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        return systemManager.getProductsDescriptionAndStatistics();
    }
}