package com.example.schoolorganizer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class MainRESTController {
    @RequestMapping("/signup")
    public ModelAndView getRegisterPage() {
        ModelAndView registerPage = new ModelAndView();
        registerPage.setViewName("signup");
        return registerPage;
    }
    @RequestMapping("/signin")
    public ModelAndView getLoginPage() {
        ModelAndView loginPage = new ModelAndView();
        loginPage.setViewName("signin");
        return loginPage;
    }
}