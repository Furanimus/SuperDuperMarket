package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ShowProductsInfo implements ICommand {
    @Override
    public String getDescription() {
        return "View all available products";
    }

    @Override
    public Object execute(SystemManagerSingleton systemManager) {
        systemManager.viewAvailableProducts();
        return "Not impelemented yet";
    }
}
