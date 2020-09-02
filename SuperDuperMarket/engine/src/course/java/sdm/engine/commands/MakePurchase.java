package course.java.sdm.engine.commands;

import course.java.sdm.engine.managers.EngineManagerSingleton;

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
    public Object execute(EngineManagerSingleton systemManager) {


        return "";
    }
}