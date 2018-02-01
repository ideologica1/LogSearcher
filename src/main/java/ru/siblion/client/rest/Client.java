package ru.siblion.client.rest;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.siblion.service.accessory.SearchInfoFactory;
import ru.siblion.util.InputDataValidator;
import ru.siblion.service.model.request.SearchInfo;
import ru.siblion.service.model.response.CorrectionCheckResult;
import ru.siblion.service.model.response.LogSearchResult;
import ru.siblion.service.model.response.SearchInfoResult;

@Component
public class Client {

  @Autowired
  private SearchInfo searchInfo;

  @Autowired
  private CorrectionCheckResult correctionCheckResult;

  @Autowired
  private SearchInfoFactory searchInfoFactory;

  @Autowired
  private LogSearchResult logSearchResult;

  @Autowired
  private InputDataValidator inputDataValidator;


  @Autowired
  private SearchInfoResult searchInfoResult;

  public String getResponse() throws ConfigurationException {
    corectionCheck(searchInfo);
    if (isErrorOccured(correctionCheckResult)) {
    }
    else {
      if (searchInfo.getRealization()) {
        searchInfoResult = logSearchSync(searchInfo);
        return "searchResult.xhtml";
      }
      else {
        logSearchResult = logSearchAsync(searchInfo);
      }

    }
    return null;
  }

  private SearchInfoResult logSearchSync(SearchInfo searchInfo) {
    RestTemplate restTemplate = new RestTemplate();
    SearchInfoResult searchInfoResult = restTemplate.getForObject("http://localhost:7001/LogsFinderSpring/results", SearchInfoResult.class);
    return searchInfoResult;
  }

  private LogSearchResult logSearchAsync(SearchInfo searchInfo) {
    RestTemplate restTemplate = new RestTemplate();
    LogSearchResult logSearchResult = restTemplate.getForObject("http://localhost:7001/LogsFinderSpring/results", LogSearchResult.class);
    return logSearchResult;
  }

  private void corectionCheck(SearchInfo searchInfo) throws ConfigurationException {
    inputDataValidator.correctionCheck(searchInfo);
  }

  private boolean isErrorOccured(CorrectionCheckResult correctionCheckResult) {
    return correctionCheckResult.getErrorCode() != 0;
  }

}
