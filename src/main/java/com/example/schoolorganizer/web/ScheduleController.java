package com.example.schoolorganizer.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ScheduleController {
    @GetMapping("/schedule")
    public String getSchedule() {
        return "schedule";
    }
}
