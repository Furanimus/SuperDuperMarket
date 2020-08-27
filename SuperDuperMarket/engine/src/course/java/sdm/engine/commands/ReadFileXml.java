package course.java.sdm.engine.commands;

import course.java.sdm.engine.SystemManagerSingleton;
import course.java.sdm.engine.xml.FileValidator;
import course.java.sdm.engine.xml.JAXBObjectsToMyObjects;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class ReadFileXml implements MenuItem {
    private String msg = "";
    FileValidator fileValidator = new FileValidator();
    JAXBObjectsToMyObjects reader = new JAXBObjectsToMyObjects();

    @Override
    public String showMe() {
        return "Load a file to the system from XML";
    }

    @Override
    public Object activate(SystemManagerSingleton systemManager) {
        String path = systemManager.getFilePath();
        try {
            if (validateFile(path)) {
                copyFromXml(path);
                msg = "File was loaded successfully";
                if(!systemManager.getIsFileLoaded()) {
                    systemManager.fileLoaded();
                }
            }
        }
        catch(FileNotFoundException ex) {
            msg = "File was not found. Please enter a valid path to file. ";
        } catch(JAXBException ex) {
            msg = "JAXB Exception:" + ex.getMessage();
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
    private void copyFromXml(String filePath) throws FileNotFoundException, JAXBException {
        reader.jaxbObjectToMyObject();
    }
}