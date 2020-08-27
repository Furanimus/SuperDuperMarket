package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

//TODO change execute to return a String and move it to System module

public interface MenuItem {
    String showMe();

    Object activate(SystemManagerSingleton systemManager);
}