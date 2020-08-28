package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ShowVendorsInfo implements MenuItem {

    @Override
    public String showMe() {
        return "Show all vendors information";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        return systemManager.getVendorManager().getVendorsInfo();
    }
}
