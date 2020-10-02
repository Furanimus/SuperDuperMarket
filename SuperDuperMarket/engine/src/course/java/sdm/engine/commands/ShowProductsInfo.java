package course.java.sdm.engine.commands;

import course.java.sdm.engine.managers.EngineManagerSingleton;

public class ShowProductsInfo implements MenuItem {
    @Override
    public String showMe() {
        return "View all available products";
    }

    @Override
    public Object execute(EngineManagerSingleton engineManager) {
        return engineManager.getProductsDescriptionAndStatistics();
    }
}