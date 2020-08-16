package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ReadFileXml implements ICommand{

    @Override
    public String getDescription() {
        return "Load a file to the system from XML";
    }

    @Override
    public void execute(SystemManagerSingleton systemManager) {

    }
}