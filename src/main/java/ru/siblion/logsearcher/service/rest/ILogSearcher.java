package ru.siblion.logsearcher.service.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.response.LogSearchResult;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;

import javax.jws.WebService;

public interface ILogSearcher {

    LogSearchResult logsSearchAsync(SearchInfo searchInfo);

    SearchInfoResult logSearchSync(SearchInfo searchInfo);
}
