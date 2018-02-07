package ru.siblion.logsearcher.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.siblion.logsearcher.service.accessory.FileManager;
import ru.siblion.logsearcher.service.accessory.SearchResultManager;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.response.LogSearchResult;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;


@RestController
public class LogsSearcherREST {

    private SearchResultManager searchResultManager;

    private FileManager fileManager;

    @RequestMapping(value = "/creating", method = RequestMethod.POST,
            produces = {"application/json", "application/xml"},
            consumes = {"application/json","application/xml"})
    public LogSearchResult logsSearchAsync(@RequestBody SearchInfo searchInfo) {
        try {
            LogSearchResult logSearchResult = new LogSearchResult();
            if (fileSearch(searchInfo)) {
                logSearchResult.setResponse("Путь до интересующего файла: " + fileManager.getFileAbsolutePath());
            } else {
                generateFile(searchInfo);
                logSearchResult.setResponse("Путь до интересующего файла: " + fileManager.getFileAbsolutePath());
            }

            return logSearchResult;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @RequestMapping(value = "/results", method = RequestMethod.POST,
            produces = {"application/json", "application/xml"},
            consumes = {"application/json","application/xml"})
    public @ResponseBody SearchInfoResult logSearchSync(@RequestBody SearchInfo searchInfo) {
        try {
            return searchResultManager.searchLogs(searchInfo);
        }

        catch (Exception e) {
            throw new RuntimeException();
        }
    }


    private boolean fileSearch(SearchInfo searchInfo) {
        try {
            return fileManager.fileSearch(searchInfo);
        }

        catch (Exception e) {

            throw new RuntimeException();
        }
    }


    private void generateFile(SearchInfo searchInfo)  {
        try {
            String absolutePath = fileManager.generateFileAbsolutePath(searchInfo.getFileExtension().toString());
            fileManager.setFileAbsolutePath(absolutePath);
            fileManager.generateFile(searchInfo);
        }

        catch (Exception e) {

            throw new RuntimeException();
        }
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
