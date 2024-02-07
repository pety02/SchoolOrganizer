package com.example.schoolorganizer.web;

import java.util.*;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final IDAO<Task, TaskDTO> taskDAO;

    @Autowired
    public TaskController(TaskService taskService, IDAO<Task, TaskDTO> taskDAO) {

        this.taskService = taskService;
        this.taskDAO = taskDAO;
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

    @PostMapping("/tasks/create")
    public String createNewTask() {
        return "";
    }

    @PostMapping("tasks/update/{id}")
    public String updateTaskById(@PathVariable Long id) {
        return "";
    }

    @PostMapping("tasks/delete/{id}")
    public String deleteTaskById(@PathVariable Long id) {
        return "";
    }
}