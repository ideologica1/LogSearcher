package ru.siblion.logsearcher.service.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.response.LogSearchResult;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;

@Component
public class ServiceConsumer {

    private static final String SYNC_URI = "http://localhost:7001/Spring/results";

    private static final String ASYNC_URI = "http://localhost:7001/Spring/creating";

    public SearchInfoResult searchLogsSync(SearchInfo searchInfo) {
        RestTemplate restTemplate = new RestTemplate();
        SearchInfoResult searchInfoResult = restTemplate.postForObject(SYNC_URI, searchInfo, SearchInfoResult.class);
        return searchInfoResult;
    }

    public LogSearchResult searchLogsAsync(SearchInfo searchInfo) {
        RestTemplate restTemplate = new RestTemplate();
        LogSearchResult logSearchResult = restTemplate.postForObject(ASYNC_URI, searchInfo, LogSearchResult.class);
        return logSearchResult;
    }
}
