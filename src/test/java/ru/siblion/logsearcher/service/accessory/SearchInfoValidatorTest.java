package ru.siblion.logsearcher.service.accessory;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

import org.mockito.InjectMocks;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;
import ru.siblion.logsearcher.util.Error;
import ru.siblion.logsearcher.util.FileExtension;

import java.util.*;

import static org.mockito.Mockito.*;


public class SearchInfoValidatorTest {

    @InjectMocks
    private Validator searchInfoValidator = new SearchInfoValidator();


    @Test
    @DisplayName("Getting an extension absence error when it is not selected in async call")
    public void correctionCheckExtensionAbsenceAsync() throws Exception {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn("AdminServer");
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(null);
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFrom(new Date());
        significantDateInterval.setDateTo(new Date());
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();
        dateIntervals.add(significantDateInterval);
        when(searchInfo.getDateInterval()).thenReturn(dateIntervals);

        Error expectedError = Error.EXTENSION_ABSENCE;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }

    @Test
    @DisplayName("Getting an incorrect resource name when logs location is not selected or doesn't exist")
    void correctionCheckIncorrectResourceName() throws ConfigurationException {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn(null);
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(FileExtension.DOC);
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFrom(new Date());
        significantDateInterval.setDateTo(new Date());
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();
        dateIntervals.add(significantDateInterval);
        when(searchInfo.getDateInterval()).thenReturn(dateIntervals);

        Error expectedError = Error.LOGS_LOCATION;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }

    @Test
    void correctionCheckDateIsNullOrEmpty() throws ConfigurationException {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn("AdminServer");
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(FileExtension.DOC);
        when(searchInfo.getDateInterval()).thenReturn(null);

        Error expectedError = Error.TIME_FORMAT;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1",
            "1",
            "29-02-2017 12:00:00",
            "28-02-2017 25:00:00"})
    @DisplayName("Getting a time format error when beginning of the interval has incorrect format")
    void correctionCheckIncorrectDateFrom(String incorrectDate) throws ConfigurationException {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn("AdminServer");
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(FileExtension.DOC);
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFromString(incorrectDate);
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();
        dateIntervals.add(significantDateInterval);
        when(searchInfo.getDateInterval()).thenReturn(dateIntervals);

        Error expectedError = Error.TIME_FORMAT;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1",
            "1",
            "29-02-2017 12:00:00",
            "28-02-2017 25:00:00"})
    @DisplayName("Getting a time format error when end of the interval has incorrect format")
    void correctionCheckIncorrectDateTo(String incorrectDate) throws ConfigurationException {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn("AdminServer");
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(FileExtension.DOC);
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFromString("16-02-2018 13:00:00");
        significantDateInterval.setDateToString(incorrectDate);
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();
        dateIntervals.add(significantDateInterval);
        when(searchInfo.getDateInterval()).thenReturn(dateIntervals);

        Error expectedError = Error.TIME_FORMAT;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }

    @Test
    @DisplayName("Getting an error when beginning of interval exceed end")
    void correctionCheckDateFromExceedDateTo() throws ConfigurationException {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn("AdminServer");
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(FileExtension.DOC);
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFromString("16-02-2018 13:00:00");
        significantDateInterval.setDateToString("15-02-2018 13:00:00");
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();
        dateIntervals.add(significantDateInterval);
        when(searchInfo.getDateInterval()).thenReturn(dateIntervals);

        Error expectedError = Error.FROM_EXCEED_TO;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }

    @Test
    @DisplayName("Getting an error when beginning of interval exceed current time")
    void correctionCheckDateFromExceedPresentTime() throws ConfigurationException {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn("AdminServer");
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(FileExtension.DOC);
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFromString("15-02-2022 13:00:00");
        significantDateInterval.setDateToString("15-02-2018 13:00:00");
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();
        dateIntervals.add(significantDateInterval);
        when(searchInfo.getDateInterval()).thenReturn(dateIntervals);

        Error expectedError = Error.FROM_EXCEED_PRESENT;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }

    @Test
    @DisplayName("Getting null when all parameters are correct")
    void correctionCheckAllParametersIsCorrect() throws ConfigurationException {

        SearchInfo searchInfo = mock(SearchInfo.class);
        when(searchInfo.getLocation()).thenReturn("AdminServer");
        when(searchInfo.getRealization()).thenReturn(true);
        when(searchInfo.getFileExtension()).thenReturn(FileExtension.DOC);
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFromString("16-02-2018 13:00:00");
        significantDateInterval.setDateToString("16-02-2018 13:05:00");
        List<SignificantDateInterval> dateIntervals = new ArrayList<>();
        dateIntervals.add(significantDateInterval);
        when(searchInfo.getDateInterval()).thenReturn(dateIntervals);

        Error expectedError = null;
        Error actualError = searchInfoValidator.correctionCheck(searchInfo);
        searchInfoValidator.correctionCheck(searchInfo);
        assertThat(expectedError).isEqualTo(actualError);

    }
}