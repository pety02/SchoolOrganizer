package com.example.schoolorganizer.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeRESTController {
    @GetMapping
    public String getHome() {
        return "home";
    }
}
