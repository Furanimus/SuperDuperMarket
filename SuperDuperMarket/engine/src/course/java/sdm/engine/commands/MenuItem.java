package course.java.sdm.engine.commands;

import course.java.sdm.engine.managers.EngineManagerSingleton;

//TODO change execute to return a String and move it to System module

public interface MenuItem {
    String showMe();

    Object execute(EngineManagerSingleton systemManager);
}