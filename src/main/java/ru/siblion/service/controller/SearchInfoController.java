package ru.siblion.service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.siblion.service.entity.request.SearchInfo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchInfoController {

    private SearchInfo searchInfo;

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    @RequestMapping("/searchinfo")
    public String showResultsPage(HttpServletRequest request) {
        searchInfo.setRegularExpression(request.getParameter("regex"));
        searchInfo.setLocation(request.getParameter("location"));
        searchInfo.setFileExtention(request.getParameter("fileExtension"));
        return "results";
    }

    @Autowired
    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }
}
