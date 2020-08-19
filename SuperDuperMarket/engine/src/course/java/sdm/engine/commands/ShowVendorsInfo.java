package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ShowVendorsInfo implements ICommand {

    @Override
    public String getDescription() {
        return "Show all vendors information";
    }

    @Override
    public Object execute(SystemManagerSingleton systemManager) {
        //TODO iterate over all objects and print to console
        return "Not Implemented yet";
    }
}
