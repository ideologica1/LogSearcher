package ru.siblion.logsearcher.service.generator;

import org.springframework.stereotype.Component;
import ru.siblion.logsearcher.service.model.XMLModel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;

@Component
public class XmlStreamCreator implements StreamCreator {

    public ByteArrayOutputStream getModelAsStream(XMLModel xmlModel) {
        try {
            JAXBContext context = JAXBContext.newInstance(XMLModel.class);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(xmlModel, out);
            return out;
        }
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public XmlStreamCreator() {
    }
}
