package com.example.schoolorganizer.web;

import java.util.*;

import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.CalendarEventService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

/**
 * This class describes a ScheduleController. A class that
 * manages with users' schedules.
 *
 * @author Petya Licheva
 */
@Controller
@Slf4j
public class ScheduleController {
    private final CalendarEventService calendarService;

    /**
     * General purpose constructor of the ScheduleController class.
     *
     * @param calendarService the event service object.
     */
    @Autowired
    public ScheduleController(CalendarEventService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * This method shows all definite user's calendar events on a real calendar snippet.
     *
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/schedule")
    public String getSchedule(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedIn = (UserDTO) httpSession.getAttribute("user");

        List<CalendarEventDTO> events = (List<CalendarEventDTO>) httpSession.getAttribute("foundEvents");
        model.addAttribute("foundEvents", events);
        model.addAttribute("createdEvent", new CalendarEventDTO());
        model.addAttribute("allEvents", calendarService.getAllEventsByUserId(loggedIn.getUserId()));
        return "schedule";
    }

    /**
     * This method crates a new calendar event and save it in the database.
     *
     * @param calendarEvent      the calendar event object.
     * @param binding            the binding result object.
     * @param model              the model object.
     * @param redirectAttributes the redirect attributes object.
     * @param httpSession        the http session object.
     * @return a html page via the result of the http request.
     */
    @PostMapping("/schedule/create")
    public String createNewCalendarEvent(@Valid @ModelAttribute CalendarEventDTO calendarEvent,
                                         BindingResult binding,
                                         Model model,
                                         RedirectAttributes redirectAttributes,
                                         HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error creating new task: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("createdEvent", calendarEvent);
            model.addAttribute("allEvents", calendarService.getAllEventsByUserId(loggedUser.getUserId()));
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdEvent", binding);
            return "redirect:/schedule";
        }
        try {
            calendarEvent.setCreatedBy(loggedUser);
            if (calendarEvent.getEndDate() == null) {
                calendarEvent.setEndDate(calendarEvent.getStartDate());
            }
            CalendarEventDTO createdEvent = calendarService.createEventByUserId(loggedUser.getUserId(), calendarEvent).orElseThrow();
            if (!redirectAttributes.containsAttribute("createdEvent")) {
                redirectAttributes.addFlashAttribute("createdEvent", createdEvent);
            }
            model.addAttribute("createdEvent", createdEvent);
            model.addAttribute("allEvents", calendarService.getAllEventsByUserId(loggedUser.getUserId()));
            return "redirect:/schedule";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("createdEvent")) {
                redirectAttributes.addFlashAttribute("createdEvent", calendarEvent);
                model.addAttribute("allEvents", calendarService.getAllEventsByUserId(loggedUser.getUserId()));
            }
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/schedule";
        }
    }

    /**
     * This method gets definite user's all calendar events with the same title
     * and shows details about them.
     *
     * @param title       the calendar event's title.
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/schedule/search")
    public String searchEvents(@RequestParam("title") String title,
                               HttpSession httpSession,
                               Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedInUser = (UserDTO) httpSession.getAttribute("user");
        List<CalendarEventDTO> events = calendarService.searchByTitle(loggedInUser.getUserId(), title);

        httpSession.setAttribute("foundEvents", events);
        return "redirect:/schedule";
    }

    /**
     * This method deletes a definite user's calendar event by its own id.
     *
     * @param id          the calendar event's id.
     * @param httpSession the http session object.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/schedule/delete/{id}")
    public String deleteEvent(@PathVariable Long id,
                              HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedInUser = (UserDTO) httpSession.getAttribute("user");

        calendarService.deleteByID(id);
        httpSession.setAttribute("foundEvents", null);
        return "redirect:/schedule";
    }
}