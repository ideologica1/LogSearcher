package ru.siblion.logsearcher.service.generator;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.siblion.logsearcher.service.model.XMLModel;
import ru.siblion.logsearcher.util.FileExtension;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Component
public class PdfCreator implements FileCreator {

    @Autowired
    private XmlStreamCreator streamCreator;

    private final FileExtension fileExtension = FileExtension.PDF;

    public PdfCreator() {
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void generateFile(String filePath, XMLModel xmlModel) {

        OutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        ByteArrayOutputStream modelAsStream = streamCreator.getModelAsStream(xmlModel);

        try {
            byte[] model = modelAsStream.toByteArray();
            modelAsStream.close();
            inputStream = new ByteArrayInputStream(model);
            File xsltFile = new File("C:/Java/LogsFinderSpring/src/main/resources/templates/PDFtemplate.xsl");
            // the XML file which provides the input
            StreamSource xmlSource = new StreamSource(inputStream);
            // create an instance of fop factory
            FopFactory fopFactory = FopFactory.newInstance(new File("C:/Java/LogsFinderSpring/src/main/resources/fopconf.xml"));
            // a user agent is needed for transformation
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // Setup output
            outputStream = new FileOutputStream(filePath);

            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);
            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());
            // Start XSLT transformation and FOP processing, that's where the XML is first transformed to XSL-FO and then PDF is created
            transformer.transform(xmlSource, res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setStreamCreator(XmlStreamCreator streamCreator) {
        this.streamCreator = streamCreator;
    }

    public StreamCreator getStreamCreator() {
        return streamCreator;
    }

}
