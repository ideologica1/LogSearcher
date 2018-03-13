package ru.siblion.logsearcher.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.siblion.logsearcher.service.model.response.SearchInfoResult;

@Controller
@RequestMapping(value = "/results")
public class ResultsController {

    @RequestMapping(method = RequestMethod.GET)
    public String getResults(Model model) {
        model.addAttribute(new SearchInfoResult());
        return "results";
    }
}
