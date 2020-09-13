package course.java.sdm.engine.commands;

import course.java.sdm.engine.exceptions.LocationOutOfBoundsException;
import course.java.sdm.engine.managers.EngineManagerSingleton;
import course.java.sdm.engine.xml.FileValidator;
import course.java.sdm.engine.xml.ObjectsDecoder;
import course.java.sdm.engine.xml.jaxbobjects.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBException;
import java.io.*;

public class ReadFileXml implements MenuItem {
    private String msg = "";
    FileValidator fileValidator = new FileValidator();
    ObjectsDecoder reader = new ObjectsDecoder();

    @Override
    public String showMe() {
        return "Load a file to the system from XML";
    }

    @Override
    public Object execute(EngineManagerSingleton systemManager) {
        String path = systemManager.getFilePath();
        try {
            File file = new File(path);
            if (fileValidator.validateExistence(file)) {
                try (InputStream inputStream = new FileInputStream(file)) {
                    SuperDuperMarketDescriptor sdmDescriptor = reader.deserializeFrom(inputStream);
                    if (fileValidator.validateAppWise(sdmDescriptor)) {
                        copyFromXml(sdmDescriptor);
                        msg = "File loaded successfully";
                        if (!systemManager.getIsFileLoaded()) {
                            systemManager.fileLoaded();
                        }
                    }
                }
            }
        } catch (LocationOutOfBoundsException e){
            msg = "There is a location in file that is not within range of coordinates map";
        } catch(FileNotFoundException ex) {
            msg = "File was not found. Please enter a valid path to file. ";
        } catch (IOException e) {
            msg = "IO exception:" + e.getMessage();
        } catch(JAXBException ex) {
            msg = "JAXB Exception:" + ex.getMessage();
        } catch(Exception ex) {
            msg = ex.getMessage();
        }
        return msg;
    }

    private boolean validateFile(String path) {
        return true; //(fileValidator.validateAppWise());
    }

    //TODO
    private void copyFromXml(SuperDuperMarketDescriptor sdmDescriptor) throws IOException, JAXBException {
        reader.jaxbObjectToMyObject(sdmDescriptor);
    }
}