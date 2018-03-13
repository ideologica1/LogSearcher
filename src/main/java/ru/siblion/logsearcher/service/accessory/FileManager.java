package ru.siblion.logsearcher.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.siblion.logsearcher.service.dao.DaoService;
import ru.siblion.logsearcher.service.generator.CreatorFactory;
import ru.siblion.logsearcher.service.generator.FileCreator;
import ru.siblion.logsearcher.service.model.XMLModel;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;
import ru.siblion.logsearcher.service.model.response.CorrectionCheckResult;
import ru.siblion.logsearcher.util.FileExtension;

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
    private XMLModel XMLModel;

    @Autowired
    private DaoService daoService;

    @Autowired
    private LogSearcher fileLogSearcher;

    private String fileAbsolutePath;

    @Autowired
    private CreatorFactory creatorFactory;


    @Async("threadPoolTaskExecutor")
    public void generateFile(SearchInfo searchInfo) {
        try {
            XMLModel.setSearchInfo(searchInfo);
            XMLModel.setSearchInfoResult(fileLogSearcher.searchLogs(searchInfo));
            FileExtension fileExtension = searchInfo.getFileExtension();
            String fileName = extractFileName(fileAbsolutePath);
            daoService.saveCreatedFile(searchInfo, fileName);
            FileCreator fileCreator = creatorFactory.getFileGenerator(fileExtension);
            if (correctionCheckResult.getErrorCode() == 0) {
                fileCreator.generateFile(fileAbsolutePath, XMLModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateFileAbsolutePath(String extension) throws ConfigurationException {
        PropertiesConfiguration conf = new PropertiesConfiguration("application.properties");
        return conf.getString("created_files_location") + "logs_" + new Date().getTime() + "." + extension.toLowerCase();
    }

    private String extractFileName(String absolutePath) {
        String[] splittedAbsolutePath = absolutePath.split("//");
        return splittedAbsolutePath[splittedAbsolutePath.length - 1];
    }

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }


    public boolean fileSearch(SearchInfo searchInfo) throws SQLException, ParseException, ConfigurationException {

        List<SignificantDateInterval> searchInfoDateIntervals = searchInfo.getDateInterval();
        List<String> filesName = daoService.getFilteredExistingFiles(searchInfo);
        List<SignificantDateInterval> existingFilesDateIntervals;

        if (!filesName.isEmpty()) {
            for (String fileName : filesName) {
                existingFilesDateIntervals = daoService.getDateIntervals(fileName);
                if (isIntervalsCovered(searchInfoDateIntervals, existingFilesDateIntervals) && !isCoveragePercentageExceed(searchInfoDateIntervals, existingFilesDateIntervals)) {

                    PropertiesConfiguration conf = new PropertiesConfiguration("C:/Java/LogsFinderSpring/src/main/resources/application.properties");
                    fileAbsolutePath = conf.getString("created_files_location") + fileName;
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
        long sumOfIntervalsRequest = 0;
        long sumOfIntervalsResponse = 0;
        for (SignificantDateInterval significantDateIntervalRequest : request) {
            long requestDateFrom = significantDateIntervalRequest.getDateFrom().getTime();
            long requestDateTo = significantDateIntervalRequest.getDateTo().getTime();
            sumOfIntervalsRequest += (requestDateTo - requestDateFrom);
        }

        for (SignificantDateInterval significantDateIntervalResponse : response) {
            long responseDateFrom = significantDateIntervalResponse.getDateFrom().getTime();
            long responseDateTo = significantDateIntervalResponse.getDateTo().getTime();
            sumOfIntervalsResponse += (responseDateTo - responseDateFrom);
        }

        float percetnageExceed = (float) sumOfIntervalsResponse / sumOfIntervalsRequest;
        return (percetnageExceed > 1.1);
    }

    public void setFileAbsolutePath(String absolutePath) {
        this.fileAbsolutePath = absolutePath;
    }


    public void setCorrectionCheckResult(CorrectionCheckResult correctionCheckResult) {
        this.correctionCheckResult = correctionCheckResult;
    }

    public void setXMLModel(ru.siblion.logsearcher.service.model.XMLModel XMLModel) {
        this.XMLModel = XMLModel;
    }

    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }


    public void setFileLogSearcher(LogSearcher fileLogSearcher) {
        this.fileLogSearcher = fileLogSearcher;
    }
}
