package com.example.schoolorganizer.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("api")
public class UserRESTController {
    @GetMapping("/signup")
    public ModelAndView getSignUpPage() {
        ModelAndView mandV = new ModelAndView();
        mandV.setViewName("signup");
        return mandV;
    }
}