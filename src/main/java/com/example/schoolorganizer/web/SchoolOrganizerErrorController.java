package com.example.schoolorganizer.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class describes a controller that manages
 * with viewing a custom error page when its needed.
 *
 * @author Petya Licheva
 */
@Controller
public class SchoolOrganizerErrorController implements ErrorController {

    /**
     * This class shows a custom error page.
     *
     * @return a html page via the result of the http request.
     */
    @RequestMapping("/error")
    public String handleError() {
        return "error-page";
    }
}