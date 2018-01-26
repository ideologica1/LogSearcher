package ru.siblion.service.processors;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import ru.siblion.service.entity.request.SearchInfo;
import ru.siblion.service.entity.response.SearchInfoResult;

import javax.jws.WebMethod;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;

@RestController
public class LogsSearcherREST {

    private SearchResultManager searchResultManager;

    private FileManager fileManager;

  /*  @RequestMapping(value = "/results", method = RequestMethod.GET)
    public LogSearchResult logsSearchAsync(@RequestBody SearchInfo searchInfo) throws SQLException, ParseException, IOException, JAXBException, SAXException, ConfigurationException, TransformerException {
        LogSearchResult logSearchResult = new LogSearchResult();
        if (fileSearch(searchInfo)) {
            logSearchResult.setResponse(fileManager.getFileAbsolutePath());
        } else {
            generateFile(searchInfo);
            logSearchResult.setResponse(fileManager.getFileAbsolutePath());
        }

        return logSearchResult;
    } */

    @RequestMapping(value = "/resultsrest", method = RequestMethod.POST,
            produces = {"application/json", "application/xml"},
            consumes = {"application/json","application/xml"})
    public @ResponseBody SearchInfoResult logSearchSync(@RequestBody SearchInfo searchInfo) throws IOException, ConfigurationException {
        SearchInfoResult  searchInfoResult = searchResultManager.searchLogs(searchInfo);
        return  searchInfoResult;
    }

 /*   @WebMethod(exclude = true)
    private boolean fileSearch(SearchInfo searchInfo) throws SQLException, ParseException {
        return fileManager.fileSearch(searchInfo);
    } */

    @WebMethod(exclude = true)
    private void generateFile(SearchInfo searchInfo) throws TransformerException, IOException, JAXBException, SQLException, SAXException, ConfigurationException {
        String absolutePath = fileManager.generateFileAbsolutePath(searchInfo.getFileExtention());
        fileManager.setFileAbsolutePath(absolutePath);
        fileManager.generateFile(searchInfo);
    }

    @Autowired
    public void setSearchResultManager(SearchResultManager searchResultManager) {
        this.searchResultManager = searchResultManager;
    }

    @Autowired
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public LogsSearcherREST() {
    }
}
