package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;

public class ReadFileXml implements ICommand{
    private String msg = "";
    //private String path = "";
    @Override
    public String getDescription() {
        return "Load a file to the system from XML";
    }

    @Override
    public String execute(SystemManagerSingleton systemManager) {
        String path = systemManager.getFilePath();
        try {
            if (validateFile(path)) {
                readFromXml(path);
                msg = "File was loaded successfully";
                if(systemManager.getIsFileLoaded()) {
                    systemManager.fileLoaded();
                }
            }
        }
        catch(Exception ex) {
            msg = "Unknown exception occurred:" + ex.getMessage();
        }
        return msg;
    }

    private boolean validateFile(String path) {
        return (validateExistence() && validateAppWise());
    }
    //TODO
    private boolean validateAppWise() {
        return true;
    }
    //TODO
    private boolean validateExistence() {
        return true;
    }
    //TODO
    private void readFromXml(String filePath) {
    }
}