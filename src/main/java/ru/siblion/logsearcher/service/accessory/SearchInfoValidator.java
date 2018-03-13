package ru.siblion.logsearcher.service.accessory;



import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.util.Error;
import ru.siblion.logsearcher.util.FileExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class SearchInfoValidator implements Validator {

    /**
     * {@inheritDoc}
     */

    @Nullable
    public Error correctionCheck(SearchInfo searchInfo) {

        if (searchInfo != null) {

            String location = searchInfo.getLocation();
            FileExtension fileExtension = searchInfo.getFileExtension();
            List<SignificantDateInterval> significantDateIntervals = searchInfo.getDateInterval();
            List<Error> errorList = new ArrayList<>();

            if (searchInfo.getRealization()) {
                if (!isExtensionChosen(fileExtension))
                    errorList.add(Error.EXTENSION_ABSENCE);
            }

            if (!isFilePathValid(location)) {
                errorList.add(Error.LOGS_LOCATION);
            }

            if (isMandatoryParamEmpty(location)) {
                errorList.add(Error.INPUT_PARAMETERS);
            }


            DateFormat validDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            if (significantDateIntervals != null && !significantDateIntervals.isEmpty()) {
                for (SignificantDateInterval significantDateInterval : significantDateIntervals) {
                    String dateFromAsString = significantDateInterval.getDateFromString();
                    if (dateFromAsString == null || dateFromAsString.isEmpty()) {
                        dateFromAsString = validDateFormat.format(new Date(0L));
                    }
                    String dateToAsString = significantDateInterval.getDateToString();
                    if (dateToAsString == null || dateToAsString.isEmpty()) {
                        dateToAsString = validDateFormat.format(new Date(Long.MAX_VALUE));
                    }
                    if (isDateValid(dateFromAsString, dateToAsString)) {
                        try {
                            Date dateFrom = validDateFormat.parse(dateFromAsString);
                            Date dateTo = validDateFormat.parse(dateToAsString);
                            significantDateInterval.setDateFrom(dateFrom);
                            significantDateInterval.setDateTo(dateTo);

                            if (isDateFromExceedTo(dateFrom, dateTo)) {
                                errorList.add(Error.FROM_EXCEED_TO);
                            }

                            if (isDateFromExceedPresent(dateFrom)) {
                                errorList.add(Error.FROM_EXCEED_PRESENT);
                            }

                        } catch (ParseException e) {
                            errorList.add(Error.TIME_FORMAT);
                            e.printStackTrace();
                        }
                    } else errorList.add(Error.TIME_FORMAT);
                }
            } else {
                errorList.add(Error.TIME_FORMAT);
            }

            return searchMaxErrorCode(errorList);
        }

        else return Error.INPUT_PARAMETERS;

    }

    private Error searchMaxErrorCode(List<Error> foundErrors) {

        Optional<Error> error = foundErrors.stream()
                .max((Error err1, Error err2) ->(int)(err1.getErrorCode() - err2.getErrorCode()));
        return error.orElse(null);

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

    private boolean isMandatoryParamEmpty(String location) {

        if (location != null && !location.isEmpty()) return false;
        else return true;

    }

    private boolean isFilePathValid(String path) {
        if (!(path == null) && !path.isEmpty()) {
            PropertiesConfiguration conf = null;
            try {
                conf = new PropertiesConfiguration("C:\\Java\\LogsFinderSpringRefactored\\src\\main\\resources\\application.properties");
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

    private boolean isExtensionChosen(FileExtension extension) {

        return extension != null;

    }

}
