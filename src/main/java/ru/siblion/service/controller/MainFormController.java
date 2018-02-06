package ru.siblion.service.controller;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import ru.siblion.service.accessory.SearchInfoFactory;
import ru.siblion.service.model.response.CorrectionCheckResult;
import ru.siblion.service.model.response.LogSearchResult;
import ru.siblion.service.accessory.InputDataValidator;
import ru.siblion.service.model.request.SearchInfo;
import ru.siblion.service.model.response.SearchInfoResult;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/form")
public class MainFormController {

    private static final String SYNC_URI = "http://localhost:7001/Spring/results";

    private static final String ASYNC_URI = "http://localhost:7001/Spring/creating";


    @Autowired
    private InputDataValidator inputDataValidator;

    @Autowired
    private SearchInfoFactory searchInfoFactory;


    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        return "index";
    }



    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public LogSearchResult getResults(Model model, HttpServletRequest request) throws ConfigurationException {

        SearchInfo searchInfo = searchInfoFactory.createSearchInfo(request);

        inputDataValidator.correctionCheck(searchInfo);
        CorrectionCheckResult correctionCheckResult = inputDataValidator.getCorrectionCheckResult();
        if (!searchInfo.getRealization()) {
            if (correctionCheckResult.getErrorCode() == 0) {
                RestTemplate restTemplate = new RestTemplate();
                SearchInfoResult searchInfoResult = restTemplate.postForObject(SYNC_URI, searchInfo, SearchInfoResult.class);
                model.addAttribute(searchInfoResult);
                return null;
            }
            else return null;
        }
        else {
            if (correctionCheckResult.getErrorCode() == 0) {
                RestTemplate restTemplate = new RestTemplate();
                LogSearchResult logSearchResult = restTemplate.postForObject(ASYNC_URI, searchInfo, LogSearchResult.class);
               // model.addAttribute(logSearchResult);
                return logSearchResult;
            }
            else {
                LogSearchResult logSearchResult = new LogSearchResult();
                logSearchResult.setResponse(correctionCheckResult);
                return logSearchResult;
            }
        }

    }



    public void setInputDataValidator(InputDataValidator inputDataValidator) {
        this.inputDataValidator = inputDataValidator;
    }

    public void setSearchInfoFactory(SearchInfoFactory searchInfoFactory) {
        this.searchInfoFactory = searchInfoFactory;
    }
}
