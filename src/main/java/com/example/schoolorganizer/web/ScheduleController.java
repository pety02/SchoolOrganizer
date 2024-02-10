package com.example.schoolorganizer.web;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.CalendarEvent;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.CalendarEventService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
public class ScheduleController {
    private final CalendarEventService eventService;
    private final IAdapter<User, UserDTO> userDAO;
    private final IAdapter<CalendarEvent, CalendarEventDTO> eventDAO;
    private final CalendarEventService calendarService;

    @Autowired
    public ScheduleController(CalendarEventService eventService, IAdapter<User, UserDTO> userDAO, IAdapter<CalendarEvent, CalendarEventDTO> enetDAO, CalendarEventService calendarService) {
        this.eventService = eventService;
        this.userDAO = userDAO;
        this.eventDAO = enetDAO;
        this.calendarService = calendarService;
    }

    @GetMapping("/schedule")
    public String getSchedule(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        User loggedin = (User) httpSession.getAttribute("user");

        model.addAttribute("createdEvent", new CalendarEventDTO());
        return "schedule";
    }

    @PostMapping("/schedule")
    public String createNewCalendarEvent(@Valid @ModelAttribute CalendarEventDTO calendarEvent,
                                         BindingResult binding,
                                         Model model,
                                         RedirectAttributes redirectAttributes,
                                         HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        User loggedUser = (User) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error creating new task: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("createdEvent", calendarEvent);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdEvent", binding);
            return "redirect:/schedule";
        }
        try {
            calendarEvent.setCreatedBy(userDAO.fromEntityToDTO(loggedUser));
            if (calendarEvent.getEndDate() == null) {
                calendarEvent.setEndDate(calendarEvent.getStartDate());
            }
            CalendarEventDTO createdEvent = eventDAO.fromEntityToDTO(calendarService.createEventByUserId(loggedUser.getUserId(), calendarEvent).orElseThrow());
            if (createdEvent == null) {
                String errors = "Invalid new event data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("createdEvent")) {
                    redirectAttributes.addFlashAttribute("createdEvent", calendarEvent);
                }
                return "redirect:/schedule";
            }
            if (!redirectAttributes.containsAttribute("createdEvent")) {
                redirectAttributes.addFlashAttribute("createdEvent", createdEvent);
            }
            model.addAttribute("createdEvent", createdEvent);
            return "redirect:/schedule";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("createdEvent")) {
                redirectAttributes.addFlashAttribute("createdEvent", calendarEvent);
            }
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/schedule";
        }
    }
}