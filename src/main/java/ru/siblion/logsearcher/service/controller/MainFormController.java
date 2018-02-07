package ru.siblion.logsearcher.service.controller;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.siblion.logsearcher.service.rest.ServiceConsumer;
import ru.siblion.logsearcher.service.accessory.InputDataValidator;
import ru.siblion.logsearcher.service.accessory.SearchInfoFactory;
import ru.siblion.logsearcher.service.model.response.CorrectionCheckResult;
import ru.siblion.logsearcher.service.model.response.LogSearchResult;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/form")
public class MainFormController {


    @Autowired
    private InputDataValidator inputDataValidator;

    @Autowired
    private SearchInfoFactory searchInfoFactory;

    @Autowired
    ServiceConsumer serviceConsumer;


    private Logger logger = Logger.getLogger(MainFormController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        return "index";
    }



    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public LogSearchResult getResults(Model model, HttpServletRequest request) throws ConfigurationException {


        logger.info(" started search process");


        SearchInfo searchInfo = searchInfoFactory.createSearchInfo(request);

        inputDataValidator.correctionCheck(searchInfo);
        CorrectionCheckResult correctionCheckResult = inputDataValidator.getCorrectionCheckResult();
        if (!searchInfo.getRealization()) {
            if (correctionCheckResult.getErrorCode() == 0) {
                SearchInfoResult searchInfoResult = serviceConsumer.searchLogsSync(searchInfo);
              //  model.addAttribute(searchInfoResult);
                return null;
            }
            else {
                logger.info(" has received warning \"search process was corrupted due to incorrect input data\"");
                return null;
            }
        }
        else {
            if (correctionCheckResult.getErrorCode() == 0) {
                LogSearchResult logSearchResult = serviceConsumer.searchLogsAsync(searchInfo);
               // model.addAttribute(logSearchResult);
                return logSearchResult;
            }
            else {
                logger.info(" has received warning \"search process was corrupted due to incorrect input data\"");
                LogSearchResult logSearchResult = new LogSearchResult();
                logSearchResult.setResponse(correctionCheckResult);
                return logSearchResult;
            }
        }

    }


    @RequestMapping(value = "switch", method = RequestMethod.POST)
    public void reportColorAlteration(@RequestBody String string) {
        logger.info(" switched form color to " + "#" + string.substring(3, 9));
    }



    public void setInputDataValidator(InputDataValidator inputDataValidator) {
        this.inputDataValidator = inputDataValidator;
    }

    public void setSearchInfoFactory(SearchInfoFactory searchInfoFactory) {
        this.searchInfoFactory = searchInfoFactory;
    }

    public void setServiceConsumer(ServiceConsumer serviceConsumer) {
        this.serviceConsumer = serviceConsumer;
    }
}
