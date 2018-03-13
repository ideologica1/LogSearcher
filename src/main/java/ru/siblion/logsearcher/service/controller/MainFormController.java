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
import ru.siblion.logsearcher.service.accessory.Validator;
import ru.siblion.logsearcher.service.rest.ServiceConsumer;
import ru.siblion.logsearcher.service.accessory.SearchInfoValidator;
import ru.siblion.logsearcher.service.accessory.SearchInfoFactory;
import ru.siblion.logsearcher.service.model.response.CorrectionCheckResult;
import ru.siblion.logsearcher.service.model.response.LogSearchResult;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;
import ru.siblion.logsearcher.util.Error;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/form")
public class MainFormController {


    @Autowired
    private Validator searchInfoValidator;

    @Autowired
    private SearchInfoFactory searchInfoFactory;

    @Autowired
    private ServiceConsumer serviceConsumer;

    private Logger logger = Logger.getLogger(MainFormController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        return "index";
    }


    @RequestMapping(method = RequestMethod.POST, value = "create")
    @ResponseBody
    public LogSearchResult saveFoundLogs(Model model, HttpServletRequest request) throws ConfigurationException {

        logger.info(" started search process");
        SearchInfo searchInfo = searchInfoFactory.createSearchInfo(request);
        CorrectionCheckResult correctionCheckResult = correctionCheck(searchInfo);

        if (correctionCheckResult.getErrorCode() == 0) {
            return serviceConsumer.searchLogsAsync(searchInfo);
        } else {

            logger.info(" has received warning \"search process was corrupted due to incorrect input data\"");
            LogSearchResult logSearchResult = new LogSearchResult();
            logSearchResult.setResponse(correctionCheckResult);
            return logSearchResult;

        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "show")
    public String showFoundLogs(Model model, HttpServletRequest request) {

        logger.info(" started search process");
        SearchInfo searchInfo = searchInfoFactory.createSearchInfo(request);
        CorrectionCheckResult correctionCheckResult = correctionCheck(searchInfo);
        SearchInfoResult searchInfoResult;

        if (correctionCheckResult.getErrorCode() == 0) {
            searchInfoResult = serviceConsumer.searchLogsSync(searchInfo);
        } else {
            searchInfoResult = new SearchInfoResult();
            logger.info(" has received warning \"search process was corrupted due to incorrect input data\"");
            long errorCode = correctionCheckResult.getErrorCode();
            searchInfoResult.setErrorCode(errorCode);
            searchInfoResult.setErrorMessage(Error.getErrorDescriptionByCode(errorCode));
        }

        model.addAttribute(searchInfoResult);
        return "results";
    }


    @RequestMapping(method = RequestMethod.POST, value = "switch")
    public void reportColorAlteration(@RequestBody String string) {

        logger.info(" switched form color to " + "#" + string.substring(3, 9));

    }

    private CorrectionCheckResult correctionCheck(SearchInfo searchInfo) {

        Error foundError = searchInfoValidator.correctionCheck(searchInfo);
        CorrectionCheckResult validCorrectionCheckResult = new CorrectionCheckResult();
        if (foundError == null) validCorrectionCheckResult.setErrorCode(0);
        else validCorrectionCheckResult.setErrorCode(foundError.getErrorCode());
        return validCorrectionCheckResult;

    }


    public void setSearchInfoValidator(SearchInfoValidator searchInfoValidator) {
        this.searchInfoValidator = searchInfoValidator;
    }

    public void setSearchInfoFactory(SearchInfoFactory searchInfoFactory) {
        this.searchInfoFactory = searchInfoFactory;
    }

    public void setServiceConsumer(ServiceConsumer serviceConsumer) {
        this.serviceConsumer = serviceConsumer;
    }
}
