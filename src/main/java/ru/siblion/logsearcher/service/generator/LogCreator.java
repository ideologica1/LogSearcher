package ru.siblion.logsearcher.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.siblion.logsearcher.service.model.XMLModel;
import ru.siblion.logsearcher.util.FileExtension;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class LogCreator implements FileCreator {

    @Autowired
    private StreamCreator streamCreator;

    private final FileExtension fileExtension = FileExtension.LOG;


    @Override
    public void generateFile(String filePath, XMLModel xmlModel) {
        ByteArrayOutputStream modelAsStream = streamCreator.getModelAsStream(xmlModel);
        ByteArrayInputStream input = null;
        byte[] model = modelAsStream.toByteArray();
        try {
            modelAsStream.close();
            input = new ByteArrayInputStream(model);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer tr = tf.newTransformer(new StreamSource("C:/Java/LogsFinderSpring/src/main/resources/templates/LOGtemplate.xsl"));
            tr.transform(new StreamSource(input), new StreamResult(
                    new FileOutputStream(filePath)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setStreamCreator(StreamCreator streamCreator) {
        this.streamCreator = streamCreator;
    }

}
