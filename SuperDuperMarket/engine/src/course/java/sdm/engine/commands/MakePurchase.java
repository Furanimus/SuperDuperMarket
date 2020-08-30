package course.java.sdm.engine.commands;

import course.java.sdm.engine.managers.SystemManagerSingleton;

import java.util.List;

public class MakePurchase implements MenuItem {
    private String lastPath = "";
    List<MenuItem> storesMenu;

    public MakePurchase() {
        initStoresMenu();
    }

    private void initStoresMenu() {
    }

    @Override
    public String showMe() {
        return "Make a purchase";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {


        return "";
    }
}