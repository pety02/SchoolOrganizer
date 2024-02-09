package com.example.schoolorganizer.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SchoolOrganizerErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "error-page";
    }
}