package course.java.sdm.engine.commands;

import course.java.sdm.engine.managers.SystemManagerSingleton;
import course.java.sdm.engine.managers.VendorManagerSingleton;

public class ShowVendorsInfo implements MenuItem {

    @Override
    public String showMe() {
        return "Show all vendors information";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        return VendorManagerSingleton.getInstance().getVendorsInfo();
    }
}
