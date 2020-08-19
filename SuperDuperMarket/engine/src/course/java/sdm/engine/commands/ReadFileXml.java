package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;
import course.java.sdm.engine.xml.FileValidator;

public class ReadFileXml implements ICommand{
    private String msg = "";
    FileValidator fileValidator = new FileValidator();

    @Override
    public String getDescription() {
        return "Load a file to the system from XML";
    }

    @Override
    public Object execute(SystemManagerSingleton systemManager) {
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
        return (fileValidator.validateExistence() && fileValidator.validateAppWise());
    }

    //TODO
    private void readFromXml(String filePath) {
    }
}