package com.example.schoolorganizer.web;

import java.time.LocalDate;
import java.util.*;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.model.User;
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

@Controller
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final IAdapter<Task, TaskDTO> taskDAO;
    private final IAdapter<User, LoginUserDTO> userDAO;

    @Autowired
    public TaskController(TaskService taskService, IAdapter<Task, TaskDTO> taskDAO, IAdapter<User, LoginUserDTO> userDAO) {

        this.taskService = taskService;
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    @GetMapping("/tasks")
    public String getAllUserTasksForm(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        List<Task> userTasksEntities = taskService.getAllTasksByUserId(loggedUser.getUserId());
        List<TaskDTO> userTasksDTOs = new ArrayList<>();
        for (var t : userTasksEntities) {
            userTasksDTOs.add(taskDAO.fromEntityToDTO(t));
        }
        model.addAttribute("tasks", userTasksDTOs);
        return "tasks";
    }

    @GetMapping("/tasks/{id}")
    public String getUserTaskByItsId(HttpSession httpSession,
                                     Model model,
                                     @PathVariable Long id) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");

        try {
            Task currentTaskEntity = taskService.getUserTaskByTaskId(loggedUser.getUserId(), id).orElseThrow();
            TaskDTO currentTaskDTO = taskDAO.fromEntityToDTO(currentTaskEntity);
            model.addAttribute("currentTask", currentTaskDTO);
            return "task";
        } catch (NoSuchElementException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks";
        }
    }

    @GetMapping("/tasks/create")
    public String getCreateNewTaskForm(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        model.addAttribute("createdTask", new TaskDTO());
        return "create-task";
    }

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
            TaskDTO createdTask = taskDAO.fromEntityToDTO(taskService.createNewTask(task).orElseThrow());
            if (createdTask == null) {
                String errors = "Invalid new task data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("createdTask")) {
                    redirectAttributes.addFlashAttribute("createdTask", task);
                }
                return "redirect:/tasks/create";
            }
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

    @GetMapping("/tasks/update/{id}")
    public String getUpdateNewTaskForm(@PathVariable Long id,
                                       HttpSession httpSession,
                                       Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        try {
            Task taskEntity = taskService.getUserTaskByTaskId(loggedUser.getUserId(), id).orElseThrow();
            TaskDTO taskDTO = taskDAO.fromEntityToDTO(taskEntity);

            model.addAttribute("updatedTask", taskDTO);
            return "update-task";
        } catch (NoSuchElementException e) {
            model.addAttribute("updatedTask", new TaskDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks";
        }
    }

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
            TaskDTO updatedTask = taskDAO.fromEntityToDTO(taskService.updateTaskById(id, task).orElseThrow());
            if (updatedTask == null) {
                String errors = "Invalid updating task data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("updatedTask")) {
                    redirectAttributes.addFlashAttribute("updatedTask", task);
                }
                model.addAttribute("updatedTask", new TaskDTO());
                return "redirect:/tasks/update/{id}";
            }
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