package com.example.schoolorganizer.web;

import java.time.LocalDate;
import java.util.*;

import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

/**
 *
 */
@Controller
@Slf4j
public class TaskController {
    private final TaskService taskService;

    /**
     * @param taskService
     */
    @Autowired
    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

    /**
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping("/tasks")
    public String getAllUserTasksForm(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        List<TaskDTO> userTasksDTOs = taskService.getAllTasksByUserId(loggedUser.getUserId());
        model.addAttribute("tasks", userTasksDTOs);
        httpSession.setAttribute("foundEvents", null);
        return "tasks";
    }

    /**
     * @param httpSession
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/tasks/{id}")
    public String getUserTaskByItsId(HttpSession httpSession,
                                     Model model,
                                     @PathVariable Long id) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");

        try {
            TaskDTO currentTaskDTO = taskService.getUserTaskByTaskId(loggedUser.getUserId(), id).orElseThrow();
            model.addAttribute("currentTask", currentTaskDTO);
            return "task";
        } catch (NoSuchElementException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks";
        }
    }

    /**
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping("/tasks/create")
    public String getCreateNewTaskForm(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        model.addAttribute("createdTask", new TaskDTO());
        return "create-task";
    }

    /**
     * @param task
     * @param binding
     * @param model
     * @param redirectAttributes
     * @param httpSession
     * @return
     */
    @PostMapping("/tasks/create")
    public String createNewTask(@Valid @ModelAttribute TaskDTO task,
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
            redirectAttributes.addFlashAttribute("createdTask", task);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdTask", binding);
            return "redirect:/tasks/create";
        }
        try {
            task.setCreatedBy(loggedUser);
            TaskDTO createdTask = taskService.createNewTask(task).orElseThrow();
            if (!redirectAttributes.containsAttribute("createdTask")) {
                redirectAttributes.addFlashAttribute("createdTask", createdTask);
            }
            model.addAttribute("createdTask", createdTask);
            return "redirect:/tasks";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("createdTask")) {
                redirectAttributes.addFlashAttribute("createdTask", task);
            }
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks/create";
        }
    }

    /**
     * @param id
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping("/tasks/update/{id}")
    public String getUpdateNewTaskForm(@PathVariable Long id,
                                       HttpSession httpSession,
                                       Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        try {
            TaskDTO taskDTO = taskService
                    .getUserTaskByTaskId(loggedUser
                            .getUserId(), id)
                    .orElseThrow();
            model.addAttribute("updatedTask", taskDTO);
            return "update-task";
        } catch (NoSuchElementException e) {
            model.addAttribute("updatedTask", new TaskDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks";
        }
    }

    /**
     * @param id
     * @param task
     * @param binding
     * @param model
     * @param redirectAttributes
     * @param httpSession
     * @return
     */
    @PostMapping("/tasks/update/{id}")
    public String updateTaskById(@PathVariable Long id,
                                 @Valid @ModelAttribute TaskDTO task,
                                 BindingResult binding,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error updating task: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("updatedTask", task);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "updatedTask", binding);
            model.addAttribute("updatedTask", new TaskDTO());
            return "redirect:/tasks/update/{id}";
        }
        try {
            task.setTaskId(id);
            task.setCreatedBy(loggedUser);
            TaskDTO updatedTask = taskService.updateTaskById(id, task).orElseThrow();
            if (!redirectAttributes.containsAttribute("updatedTask")) {
                redirectAttributes.addFlashAttribute("updatedTask", task);
            }
            model.addAttribute("updatedTask", updatedTask);
            return "redirect:/tasks";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("updatedTask")) {
                redirectAttributes.addFlashAttribute("updatedTask", task);
            }
            model.addAttribute("updatedTask", new TaskDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks/update/{id}";
        }
    }

    /**
     * @param id
     * @param httpSession
     * @return
     */
    @GetMapping("/tasks/delete/{id}")
    public String deleteTaskById(@PathVariable Long id,
                                 HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }

        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }
}