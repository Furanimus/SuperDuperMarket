package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ShowVendorsInfo implements MenuItem {

    @Override
    public String showMe() {
        return "Show all vendors information";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        //TODO iterate over all objects and print to console
        return systemManager.getVendorManager().getProductStatisticsAgainstVendors();
        //return "Not Implemented yet";
    }
}
