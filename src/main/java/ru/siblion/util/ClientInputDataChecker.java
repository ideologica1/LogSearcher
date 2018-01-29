package ru.siblion.util;



import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.siblion.service.accessory.LogSearchResultService;
import ru.siblion.service.entity.request.SearchInfo;
import ru.siblion.service.entity.request.SignificantDateInterval;
import ru.siblion.service.entity.response.CorrectionCheckResult;
import ru.siblion.util.Errors;
import ru.siblion.util.ErrorsComparator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ClientInputDataChecker {

    @Autowired
    private CorrectionCheckResult correctionCheckResult;

    @Autowired
    private LogSearchResultService logSearchResultService;

    public void correctionCheck(SearchInfo searchInfo) throws ConfigurationException {

        String location = searchInfo.getLocation();
        String fileExtension = searchInfo.getFileExtention();
        List<SignificantDateInterval> significantDateIntervals = searchInfo.getDateInterval();
        List<Errors> errorsList = new ArrayList<>();

        if (!isExtensionChosen(fileExtension))
            errorsList.add(Errors.EXTENSION_ABSENCE);

        if (!isFilePathValid(location)) {
            errorsList.add(Errors.LOGS_LOCATION);
        }

        if (isMandatoryParamEmpty(fileExtension, location)) {
            errorsList.add(Errors.INPUT_PARAMETERS);
        }


        for (SignificantDateInterval significantDateInterval : significantDateIntervals) {
            String dateFromAsString = significantDateInterval.getDateFromString();
            if (dateFromAsString == null || dateFromAsString.isEmpty()) {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                dateFromAsString = df.format(new Date(0L));
            }
            String dateToAsString = significantDateInterval.getDateToString();
            if (dateToAsString == null || dateToAsString.isEmpty()) {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                dateToAsString = df.format(new Date(Long.MAX_VALUE));
            }
            if (isDateValid(dateFromAsString, dateToAsString)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    Date dateFrom = simpleDateFormat.parse(dateFromAsString);
                    Date dateTo = simpleDateFormat.parse(dateToAsString);
                    significantDateInterval.setDateFrom(dateFrom);
                    significantDateInterval.setDateTo(dateTo);

                    if (isDateFromExceedTo(dateFrom, dateTo)) {
                        errorsList.add(Errors.FROM_EXCEED_TO);
                    }

                    if (isDateFromExceedPresent(dateFrom)) {
                        errorsList.add(Errors.FROM_EXCEED_PRESENT);
                    }

                } catch (ParseException e) {
                    errorsList.add(Errors.TIME_FORMAT);
                    e.printStackTrace();
                }
            }
            else errorsList.add(Errors.TIME_FORMAT);
        }

        Errors validError = searchMaxErrorCode(errorsList);
        if (!(validError == null)) {
            correctionCheckResult.setErrorCode(validError.getErrorCode());
        }
        else correctionCheckResult.setErrorCode(0);
        logSearchResultService.setLogSearchResultResponse(correctionCheckResult);

    }

    private Errors searchMaxErrorCode(List<Errors> foundErrors) {
        if (!foundErrors.isEmpty()) {
            foundErrors.sort(new ErrorsComparator());
            return foundErrors.get(foundErrors.size() - 1);
        }
        else return null;
    }

    private boolean isDateValid(String dateFrom, String dateTo) {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(dateFrom);
            simpleDateFormat.parse(dateTo);
            return true;
        }
        catch (ParseException e) {
            return false;
        }
    }

    private boolean isMandatoryParamEmpty(String fileExtension, String location) {
        if (fileExtension == null || location == null)
            return true;
        else return false;

    }

    private boolean isFilePathValid(String path) throws ConfigurationException {
        if (!(path == null)) {
            PropertiesConfiguration conf = null;
            try {
                conf = new PropertiesConfiguration("C:/Java/LogsFinderEJB/src/main/resources/application.properties");
                String absolutePath = conf.getString(path);
                if (absolutePath == null)
                    return false;
            } catch (ConfigurationException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isDateFromExceedTo(Date dateFrom, Date dateTo) {

        return dateFrom.after(dateTo);
    }

    private boolean isDateFromExceedPresent (Date dateFrom) {

        return dateFrom.after(new Date());
    }

    private boolean isExtensionChosen(String extension) {

        return extension != null;

    }

    public boolean isErrorOccur() {

        return correctionCheckResult.getErrorCode() != 0;

    }

    public CorrectionCheckResult getCorrectionCheckResult() {
        return correctionCheckResult;
    }
}
