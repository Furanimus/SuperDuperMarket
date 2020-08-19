package course.java.sdm.engine.xml;

import course.java.sdm.engine.SystemManagerSingleton;

import javax.xml.bind.*;
import java.io.File;

public class JAXBParser {

    private String xmlPath;

    public JAXBParser() {
        this.xmlPath = SystemManagerSingleton.getInstance().getFilePath();
    }

    //Do with reflection?
    public void xmlToObject() {
        File xmlFile = new File(xmlPath);
        JAXBContext jaxbContext;
        //try {
            System.out.println("");
            //jaxbContext = JAXBContext.newInstance(Employee.class);
            //Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //object = () jaxbUnmarshaller.unmarshal(xmlFile);

        //} catch (JAXBException e) {
            //e.printStackTrace();
    }
}

