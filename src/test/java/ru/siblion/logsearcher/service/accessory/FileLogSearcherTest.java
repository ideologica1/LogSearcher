package ru.siblion.logsearcher.service.accessory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.runners.MockitoJUnitRunner;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;
import ru.siblion.logsearcher.service.model.response.CorrectionCheckResult;
import ru.siblion.logsearcher.service.model.response.LogSearchResult;
import ru.siblion.logsearcher.service.model.response.ResultLogs;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FileLogSearcherTest {

    @InjectMocks
    private LogSearcher fileLogSearcher = new FileLogSearcher();

    @Mock
    private LogSearchResult logSearchResult;

    @Mock
    private CorrectionCheckResult correctionCheckResult;

    @Test
    public void noRecordsFoundMessage() {
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setRealization(false);
        searchInfo.setLocation("AdminServer");
        searchInfo.setRegularExpression("someAbstractRegularExpression");
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFrom(new Date(1514379009620L));
        significantDateInterval.setDateTo(new Date(1514379650415L));
        List<SignificantDateInterval> significantDateIntervals = new ArrayList<>();
        significantDateIntervals.add(significantDateInterval);
        searchInfo.setDateInterval(significantDateIntervals);
        when(correctionCheckResult.getErrorCode()).thenReturn((long)0);

        String expectedContent = "No records found.";
        SearchInfoResult searchInfoResult = fileLogSearcher.searchLogs(searchInfo);
        String actualError = searchInfoResult.getEmptyResultMessage();
        assertThat(expectedContent).isEqualTo(actualError);
        assertThat(searchInfoResult.getResultLogsList().isEmpty()).isEqualTo(true);
    }

    @Test
    public void returnSortedFoundRecordsWithNoRegEx() {
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setRealization(false);
        searchInfo.setLocation("AdminServer");
        SignificantDateInterval significantDateInterval = new SignificantDateInterval();
        significantDateInterval.setDateFrom(new Date(1514379009620L));
        significantDateInterval.setDateTo(new Date(1514379650415L));
        List<SignificantDateInterval> significantDateIntervals = new ArrayList<>();
        significantDateIntervals.add(significantDateInterval);
        searchInfo.setDateInterval(significantDateIntervals);
        when(correctionCheckResult.getErrorCode()).thenReturn((long)0);

        SearchInfoResult searchInfoResult = fileLogSearcher.searchLogs(searchInfo);
        assertThat(searchInfoResult.getResultLogsList().isEmpty()).isEqualTo(false);
        List<ResultLogs> resultLogs = searchInfoResult.getResultLogsList();
        for (int i = 0; i < resultLogs.size()-1; i++) {
            assertThat(resultLogs.get(i).getTimeMoment().after(resultLogs.get(i+1).getTimeMoment())).isEqualTo(false);
        }
    }

}