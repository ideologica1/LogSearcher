package ru.siblion.service.controller;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import ru.siblion.client.controller.ClientInputDataChecker;
import ru.siblion.service.entity.request.SearchInfo;
import ru.siblion.service.entity.request.SignificantDateInterval;
import ru.siblion.service.entity.response.SearchInfoResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/searchinfo")
public class DataController {

    private static final String REST_URI = "http://localhost:7001/Spring/resultsrest";

    private ClientInputDataChecker clientInputDataChecker;

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        model.addAttribute(new SearchInfo());
        model.addAttribute(new SignificantDateInterval());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getResultPage(SearchInfo searchInfo, SignificantDateInterval significantDateInterval, Model model) throws ConfigurationException {
        ArrayList<SignificantDateInterval> list = new ArrayList<>();
        list.add(significantDateInterval);
        searchInfo.setDateInterval(list);
        clientInputDataChecker.correctionCheck(searchInfo);

        RestTemplate restTemplate = new RestTemplate();
        SearchInfoResult searchInfoResult = restTemplate.postForObject(REST_URI, searchInfo, SearchInfoResult.class);
        model.addAttribute(searchInfoResult);
        return "foundlogs";
    }

    @Autowired
    public void setClientInputDataChecker(ClientInputDataChecker clientInputDataChecker) {
        this.clientInputDataChecker = clientInputDataChecker;
    }
}
