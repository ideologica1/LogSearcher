package ru.siblion.logsearcher.service.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginPageController {

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String showIndexPage() {
        return "login";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.POST)
    public String showIndexPages() {
        return "login";
    }
}
