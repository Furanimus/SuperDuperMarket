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
    public Object execute(EngineManagerSingleton engineManager) {
        String path = engineManager.getFilePath();
        try {
            File file = new File(path);
            if (fileValidator.validateExistence(file)) {
                try (InputStream inputStream = new FileInputStream(file)) {
                    SuperDuperMarketDescriptor sdmDescriptor = reader.deserializeFrom(inputStream);
                    if (fileValidator.validateAppWise(sdmDescriptor)) {
                        copyFromXml(sdmDescriptor);
                        msg = "File loaded successfully";
                        engineManager.setLastFileLoadedSuccessfully(true);
                        if (!engineManager.getIsFileLoaded()) {
                            engineManager.fileLoaded();
                        }
                    }
                }
            }
        } catch (LocationOutOfBoundsException e){
            msg = "There is a location in file that is not within range of coordinates map";
            engineManager.setLastFileLoadedSuccessfully(false);
        } catch(FileNotFoundException ex) {
            msg = "File was not found. Please enter a valid path to file. ";
            engineManager.setLastFileLoadedSuccessfully(false);
        } catch (IOException e) {
            msg = "IO exception:" + e.getMessage();
            engineManager.setLastFileLoadedSuccessfully(false);
        } catch(JAXBException ex) {
            msg = "JAXB Exception:" + ex.getMessage();
            engineManager.setLastFileLoadedSuccessfully(false);
        } catch(Exception ex) {
            msg = ex.getMessage();
            engineManager.setLastFileLoadedSuccessfully(false);
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