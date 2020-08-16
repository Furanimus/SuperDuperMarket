package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

//TODO change execute to return a String and move it to System module

public interface ICommand {
    String getDescription();

    void execute(SystemManagerSingleton systemManager);
}