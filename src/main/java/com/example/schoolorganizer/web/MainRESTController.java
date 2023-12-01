package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
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
        registerPage.getModel().put("userDTO",new RegisterUserDTO());
        return registerPage;
    }
    @RequestMapping("/signin")
    public ModelAndView getLoginPage() {
        ModelAndView loginPage = new ModelAndView();
        loginPage.setViewName("signin");
        loginPage.getModel().put("userDTO", new LoginUserDTO());
        return loginPage;
    }
}