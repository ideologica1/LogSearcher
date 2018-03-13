package ru.siblion.logsearcher.service.generator;

import org.springframework.stereotype.Component;
import ru.siblion.logsearcher.service.model.XMLModel;
import ru.siblion.logsearcher.util.FileExtension;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

@Component
public class XmlCreator implements FileCreator {

    private final FileExtension fileExtension = FileExtension.XML;

    @Override
    public void generateFile(String filePath, XMLModel xmlModel) {
        try {
            JAXBContext context = JAXBContext.newInstance(XMLModel.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = new File(filePath);
            marshaller.marshal(xmlModel, file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileExtension getFileExtension() {
        return fileExtension;
    }

}
