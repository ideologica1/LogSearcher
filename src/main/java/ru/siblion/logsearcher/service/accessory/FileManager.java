package ru.siblion.logsearcher.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.fop.apps.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.xml.sax.SAXException;
import ru.siblion.logsearcher.service.model.XMLModel;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;
import ru.siblion.logsearcher.service.model.response.CorrectionCheckResult;
import ru.siblion.logsearcher.util.FileExtension;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Component
@RequestScope
public class FileManager {

    @Autowired
    private CorrectionCheckResult correctionCheckResult;

    @Autowired
    private ru.siblion.logsearcher.service.model.XMLModel XMLModel;

    @Autowired
    private DataBaseManager dataBaseManager;

    @Autowired
    SearchResultManager searchResultManager;

    private String fileAbsolutePath;


    @Async("threadPoolTaskExecutor")
    public void generateFile(SearchInfo searchInfo) {
      try {
          XMLModel.setSearchInfo(searchInfo);
          XMLModel.setSearchInfoResult(searchResultManager.searchLogs(searchInfo));
          FileExtension fileExtension = searchInfo.getFileExtension();
          String fileName = extractFileName(fileAbsolutePath);
          dataBaseManager.recordCreatedFile(searchInfo, fileName);
          if (correctionCheckResult.getErrorCode() == 0) {
              switch (fileExtension) {
                  case XML:
                      generateXML(fileAbsolutePath);
                      break;
                  case PDF:
                      generatePDF(fileAbsolutePath);
                      break;
                  case RTF:
                      generateRTF(fileAbsolutePath);
                      break;
                  case LOG:
                      generateLOG(fileAbsolutePath);
                      break;
                  case HTML:
                      generateHTML(fileAbsolutePath);
                      break;
                  case DOC:
                      generateDOC(fileAbsolutePath);
                      break;

              }
          }
      }
      catch (Exception e) {
            e.printStackTrace();
      }
    }

    public String generateFileAbsolutePath(String extension) throws ConfigurationException {
        PropertiesConfiguration conf = new PropertiesConfiguration("C:/Java/LogsFinderEJB/src/main/resources/application.properties");
        return conf.getString("created_files") + "logs_" + new Date().getTime() + "." + extension.toLowerCase();
    }

    private String extractFileName(String absolutePath) {
        String[] splittedAbsolutePath = absolutePath.split("//");
        return splittedAbsolutePath[splittedAbsolutePath.length - 1];
    }

    private ByteArrayOutputStream getXMLasStream(XMLModel xmlModel) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(XMLModel.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(XMLModel, out);
        return out;

    }

    private void generateXML(String filePath) throws JAXBException, ConfigurationException {

        System.out.println("what happened?");
        JAXBContext context = JAXBContext.newInstance(XMLModel.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = new File(filePath);
        marshaller.marshal(XMLModel, file);

    }

    private void generatePDF(String filePath) throws JAXBException, IOException, TransformerException, SAXException, ConfigurationException {

        ByteArrayOutputStream out = getXMLasStream(XMLModel);
        byte[] XMLbytes = out.toByteArray();
        out.close();
        ByteArrayInputStream input = new ByteArrayInputStream(XMLbytes);
        File xsltFile = new File("C:/Java/LogsFinderEJB/src/main/resources/templates/PDFtemplate.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(input);
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File("C:/Java/LogsFinderEJB/src/main/resources/fopconf.xml"));
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream outputStream;
        outputStream = new FileOutputStream(filePath);

        try {
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);
            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());
            // Start XSLT transformation and FOP processing, that's where the XML is first transformed to XSL-FO and then PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            outputStream.close();
        }
    }

    private void generateRTF(String filePath) throws IOException, JAXBException, TransformerException, SAXException {

        ByteArrayOutputStream out = getXMLasStream(XMLModel);
        byte[] XMLbytes = out.toByteArray();
        out.close();
        ByteArrayInputStream input = new ByteArrayInputStream(XMLbytes);
        File xsltFile = new File("C:/Java/LogsFinderEJB/src/main/resources/templates/RTFtemplate.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(input);
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File("C:/Java/LogsFinderEJB/src/main/resources/fopconf.xml"));
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream outputStream;
        outputStream = new FileOutputStream(filePath);

        try {
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_RTF, foUserAgent, outputStream);
            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());
            // Start XSLT transformation and FOP processing, that's where the XML is first transformed to XSL-FO and then PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            outputStream.close();
        }

    }

    private void generateLOG(String filePath) throws IOException, JAXBException, TransformerException, SAXException {

        ByteArrayOutputStream out = getXMLasStream(XMLModel);
        byte[] XMLbytes = out.toByteArray();
        out.close();
        ByteArrayInputStream input = new ByteArrayInputStream(XMLbytes);

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer tr = tf.newTransformer(new StreamSource("C:/Java/LogsFinderEJB/src/main/resources/templates/LOGtemplate.xsl"));
            tr.transform(new StreamSource(input), new StreamResult(
                    new FileOutputStream(filePath)));

        } catch (Exception e) {
        }
    }

    private void generateHTML(String filePath) throws IOException, JAXBException, TransformerException, SAXException {

        ByteArrayOutputStream out = getXMLasStream(XMLModel);
        byte[] XMLbytes = out.toByteArray();
        out.close();
        ByteArrayInputStream input = new ByteArrayInputStream(XMLbytes);

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer tr = tf.newTransformer(new StreamSource("C:/Java/LogsFinderEJB/src/main/resources/templates/HTMLtemplate.xsl"));
            tr.transform(new StreamSource(input), new StreamResult(
                    new FileOutputStream(filePath)));

        } catch (Exception e) {
        }
    }

    private void generateDOC(String filePath) throws IOException, JAXBException, TransformerException, SAXException {

        ByteArrayOutputStream out = getXMLasStream(XMLModel);
        byte[] XMLbytes = out.toByteArray();
        out.close();
        ByteArrayInputStream input = new ByteArrayInputStream(XMLbytes);
        File xsltFile = new File("C:/Java/LogsFinderEJB/src/main/resources/templates/DOCtemplate.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(input);
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File("C:/Java/LogsFinderEJB/src/main/resources/fopconf.xml"));
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream outputStream;
        outputStream = new FileOutputStream(filePath);

        try {
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_RTF_ALT1, foUserAgent, outputStream);
            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());
            // Start XSLT transformation and FOP processing, that's where the XML is first transformed to XSL-FO and then PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            outputStream.close();
        }

    }

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }


    public boolean fileSearch(SearchInfo searchInfo) throws SQLException, ParseException, ConfigurationException {

        List<SignificantDateInterval> searchInfoDateIntervals = searchInfo.getDateInterval();
        List<String> filesName = dataBaseManager.getFilteredExistingFiles(searchInfo);
        List<SignificantDateInterval> existingFilesDateIntervals;

        if (!filesName.isEmpty()) {
            for (String fileName : filesName) {
                existingFilesDateIntervals = dataBaseManager.getDateIntervals(fileName);
                if (isIntervalsCovered(searchInfoDateIntervals, existingFilesDateIntervals) && !isCoveragePercentageExceed(searchInfoDateIntervals, existingFilesDateIntervals)) {

                    PropertiesConfiguration conf = new PropertiesConfiguration("C:/Java/LogsFinderEJB/src/main/resources/application.properties");
                    fileAbsolutePath = conf.getString("created_files") + fileName;
                    return true;
                }
            }
            return false;
        } else return false;
    }


    private boolean isIntervalsCovered(List<SignificantDateInterval> request, List<SignificantDateInterval> response) {
        boolean[] flags = new boolean[request.size()];
        int iterator = 0;
        boolean flag = true;
        for (SignificantDateInterval significantDateIntervalRequest : request) {
            long requestDateFrom = significantDateIntervalRequest.getDateFrom().getTime();
            long requestDateTo = significantDateIntervalRequest.getDateTo().getTime();
            for (SignificantDateInterval significantDateIntervalResponse : response) {
                long responseDateFrom = significantDateIntervalResponse.getDateFrom().getTime();
                long responseDateTo = significantDateIntervalResponse.getDateTo().getTime();
                if (responseDateFrom <= requestDateFrom && responseDateTo >= requestDateTo) {
                    flags[iterator++] = true;
                    break;
                }
            }
        }
        for (boolean bool : flags) {
            if (bool == false) flag = false;
        }

        return flag;
    }

    private boolean isCoveragePercentageExceed(List<SignificantDateInterval> request, List<SignificantDateInterval> response) {
        long sumOfItervalsRequest = 0;
        long sumOfItervalsResponse = 0;
        for (SignificantDateInterval significantDateIntervalRequest : request) {
            long requestDateFrom = significantDateIntervalRequest.getDateFrom().getTime();
            long requestDateTo = significantDateIntervalRequest.getDateTo().getTime();
            sumOfItervalsRequest += (requestDateTo - requestDateFrom);
        }

        for (SignificantDateInterval significantDateIntervalResponse : response) {
            long responseDateFrom = significantDateIntervalResponse.getDateFrom().getTime();
            long responseDateTo = significantDateIntervalResponse.getDateTo().getTime();
            sumOfItervalsResponse += (responseDateTo - responseDateFrom);
        }

        float percetnageExceed = (float) sumOfItervalsResponse / sumOfItervalsRequest;
        return (percetnageExceed > 1.1);
    }

    public void setFileAbsolutePath(String absolutePath) {
        this.fileAbsolutePath = absolutePath;
    }

    public CorrectionCheckResult getCorrectionCheckResult() {
        return correctionCheckResult;
    }

    public void setCorrectionCheckResult(CorrectionCheckResult correctionCheckResult) {
        this.correctionCheckResult = correctionCheckResult;
    }

    public ru.siblion.logsearcher.service.model.XMLModel getXMLModel() {
        return XMLModel;
    }

    public void setXMLModel(ru.siblion.logsearcher.service.model.XMLModel XMLModel) {
        this.XMLModel = XMLModel;
    }

    public DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public SearchResultManager getSearchResultManager() {
        return searchResultManager;
    }

    public void setSearchResultManager(SearchResultManager searchResultManager) {
        this.searchResultManager = searchResultManager;
    }
}
