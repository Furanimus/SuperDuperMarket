package course.java.sdm.engine.commands;

import course.java.sdm.engine.managers.SystemManagerSingleton;
import course.java.sdm.engine.xml.FileValidator;
import course.java.sdm.engine.xml.ObjectsDecoder;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFileXml implements MenuItem {
    private String msg = "";
    FileValidator fileValidator = new FileValidator();
    ObjectsDecoder reader = new ObjectsDecoder();

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
        } catch(FileNotFoundException ex) {
            msg = "File was not found. Please enter a valid path to file. ";
        } catch (IOException e) {
            msg = "IO exception:" + e.getMessage();
        }
        catch(JAXBException ex) {
            msg = "JAXB Exception:" + ex.getMessage();
        } catch(Exception ex) {
            msg = "Unknown exception occurred:" + ex.getMessage();
        }
        return msg;
    }

    private boolean validateFile(String path) {
        return (fileValidator.validateExistence() && fileValidator.validateAppWise());
    }

    //TODO
    private void copyFromXml(String filePath) throws IOException, JAXBException {
        reader.jaxbObjectToMyObject();
    }
}