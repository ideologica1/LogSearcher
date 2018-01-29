package ru.siblion.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import ru.siblion.service.entity.request.SearchInfo;
import ru.siblion.service.entity.response.LogSearchResult;
import ru.siblion.service.entity.response.SearchInfoResult;

import javax.jws.WebMethod;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@RestController
public class LogsSearcherREST {

    private SearchResultManager searchResultManager;

    private FileManager fileManager;

    @RequestMapping(value = "/creating", method = RequestMethod.POST,
            produces = {"application/json", "application/xml"},
            consumes = {"application/json","application/xml"})
    public LogSearchResult logsSearchAsync(@RequestBody SearchInfo searchInfo) throws SQLException, ParseException, IOException, JAXBException, SAXException, ConfigurationException, TransformerException {
        LogSearchResult logSearchResult = new LogSearchResult();
        if (fileSearch(searchInfo)) {
            logSearchResult.setResponse(fileManager.getFileAbsolutePath());
        } else {
            generateFile(searchInfo);
            logSearchResult.setResponse(fileManager.getFileAbsolutePath());
        }

        return logSearchResult;
    }

    @RequestMapping(value = "/results", method = RequestMethod.POST,
            produces = {"application/json", "application/xml"},
            consumes = {"application/json","application/xml"})
    public @ResponseBody SearchInfoResult logSearchSync(@RequestBody SearchInfo searchInfo) throws IOException, ConfigurationException {
        return searchResultManager.searchLogs(searchInfo);
    }


    private boolean fileSearch(SearchInfo searchInfo) throws SQLException, ParseException {
        return fileManager.fileSearch(searchInfo);
    }


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
