package com.example.schoolorganizer.web;

import com.example.schoolorganizer.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ScheduleController {
    @GetMapping("/schedule")
    public String getSchedule(HttpSession httpSession) {
        User loggedin = (User) httpSession.getAttribute("user");
        if (loggedin != null) {
            return "schedule";
        }

        return "redirect:signin";
    }
}