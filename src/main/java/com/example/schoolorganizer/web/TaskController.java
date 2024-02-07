package com.example.schoolorganizer.web;

import java.util.*;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.model.User;
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
    private final IDAO<Task, TaskDTO> taskDAO;
    private final IDAO<User, LoginUserDTO> userDAO;

    @Autowired
    public TaskController(TaskService taskService, IDAO<Task, TaskDTO> taskDAO, IDAO<User, LoginUserDTO> userDAO) {

        this.taskService = taskService;
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    @GetMapping("/tasks")
    public String getAllUserTasksForm(HttpSession httpSession, Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {
            List<Task> userTasksEntities = taskService.getAllTasksByUserId(loggedUser.getUserId());
            List<TaskDTO> userTasksDTOs = new ArrayList<>();
            for (var t : userTasksEntities) {
                userTasksDTOs.add(taskDAO.fromEntityToDTO(t));
            }
            model.addAttribute("tasks", userTasksDTOs);
            return "tasks";
        }

        return "redirect:/signin";
    }

    @GetMapping("/tasks/{id}")
    public String getUserTaskByItsId(HttpSession httpSession, Model model, @PathVariable Long id) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {
            try {
                Task currentTaskEntity = taskService.getUserTaskByTaskId(loggedUser.getUserId(), id).orElseThrow();
                TaskDTO currentTaskDTO = taskDAO.fromEntityToDTO(currentTaskEntity);
                model.addAttribute("currentTask", currentTaskDTO);
                return "task";
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return "redirect:/tasks";
            }
        }

        return "redirect:/signin";
    }

    @GetMapping("/tasks/create")
    public String getCreateNewTaskForm(HttpSession httpSession, Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {
            model.addAttribute("createdTask", new TaskDTO());
            return "create-task";
        }

        return "redirect:/signin";
    }

    @PostMapping("/tasks/create")
    public String createNewTask(@Valid @ModelAttribute TaskDTO task,
                                BindingResult binding,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/signin";
        }
        if (binding.hasErrors()) {
            log.error("Error creating new task: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("createdTask", task);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdTask", binding);
            return "redirect:/tasks";
        }
        try {
            task.setCreatedBy(userDAO.fromEntityToDTO(loggedUser));
            TaskDTO createdTask = taskDAO.fromEntityToDTO(taskService.createNewTask(task).orElseThrow());
            if (createdTask == null) {
                String errors = "Invalid new task data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("createdTask")) {
                    redirectAttributes.addFlashAttribute("createdTask", task);
                }
                return "redirect:/tasks";
            }
            if (!redirectAttributes.containsAttribute("createdTask")) {
                redirectAttributes.addFlashAttribute("createdTask", task);
            }
            model.addAttribute("createdTask", createdTask);
            return "redirect:/tasks";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("createdTask")) {
                redirectAttributes.addFlashAttribute("createdTask", task);
            }
            return "redirect:/tasks";
        }
    }

    @GetMapping("/tasks/update/{id}")
    public String getCreateNewTaskForm(@PathVariable Long id,
                                       HttpSession httpSession,
                                       Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        try {
            if (loggedUser != null) {
                Task taskEntity = taskService.getUserTaskByTaskId(loggedUser.getUserId(), id).orElseThrow();
                TaskDTO taskDTO = taskDAO.fromEntityToDTO(taskEntity);

                model.addAttribute("updatedTask", taskDTO);
                System.out.println(taskDTO.getStartDate());
                return "update-task";
            }
            model.addAttribute("updatedTask", new TaskDTO());
            return "redirect:/tasks";
        } catch (NoSuchElementException e) {
            model.addAttribute("updatedTask", new TaskDTO());
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
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            model.addAttribute("updatedTask", new TaskDTO());
            return "redirect:/signin";
        }
        if (binding.hasErrors()) {
            log.error("Error updating task: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("updatedTask", task);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "updatedTask", binding);
            model.addAttribute("updatedTask", new TaskDTO());
            return "redirect:/tasks";
        }
        try {
            task.setTaskId(id);
            task.setCreatedBy(userDAO.fromEntityToDTO(loggedUser));
            TaskDTO updatedTask = taskDAO.fromEntityToDTO(taskService.updateTaskById(id, task).orElseThrow());
            if (updatedTask == null) {
                String errors = "Invalid updating task data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("updatedTask")) {
                    redirectAttributes.addFlashAttribute("updatedTask", task);
                }
                model.addAttribute("updatedTask", new TaskDTO());
                return "redirect:/tasks";
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
            return "redirect:/tasks";
        }
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTaskById(@PathVariable Long id,
                                 HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/signin";
        }

        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }
}