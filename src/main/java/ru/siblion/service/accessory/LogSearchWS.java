package ru.siblion.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import ru.siblion.service.entity.request.SearchInfo;
import ru.siblion.service.entity.response.SearchInfoResult;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;


@WebService
public class LogSearchWS {

    @Autowired
    private SearchResultManager searchResultManager;

    @Autowired
    private FileManager fileManager;

  /*  @WebMethod
    public LogSearchResult logSearchAsync(SearchInfo searchInfo) throws ParseException, TransformerException, IOException, JAXBException, SQLException, SAXException, ConfigurationException {
        LogSearchResult logSearchResult = new LogSearchResult();
        if (fileSearch(searchInfo)) {
            logSearchResult.setResponse(fileManager.getFileAbsolutePath());
        } else {
            generateFile(searchInfo);
            logSearchResult.setResponse(fileManager.getFileAbsolutePath());
        }

        return logSearchResult;

    } */

    @WebMethod
    public SearchInfoResult logSearchSync(SearchInfo searchInfo) throws IOException, ConfigurationException {
        return searchResultManager.searchLogs(searchInfo);
    }

   /* @WebMethod(exclude = true)
    private boolean fileSearch(SearchInfo searchInfo) throws SQLException, ParseException {
        return fileManager.fileSearch(searchInfo);
    } */

    @WebMethod(exclude = true)
    private void generateFile(SearchInfo searchInfo) throws TransformerException, IOException, JAXBException, SQLException, SAXException, ConfigurationException {
        String absolutePath = fileManager.generateFileAbsolutePath(searchInfo.getFileExtention());
        fileManager.setFileAbsolutePath(absolutePath);
        fileManager.generateFile(searchInfo);
    }

    public LogSearchWS() {
    }
}
