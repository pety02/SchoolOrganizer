package com.example.schoolorganizer.web;

import java.time.LocalDate;
import java.util.*;

import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.FileService;
import com.example.schoolorganizer.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

/**
 * This class describes a TaskController. A class
 * that manages with users' tasks.
 *
 * @author Petya Licheva
 */
@Controller
@CrossOrigin("http://localhost:8080")
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final FileService fileService;


    /**
     * General purpose constructor of the TaskController class.
     *
     * @param taskService the task service object.
     * @param fileService the file Service
     */
    @Autowired
    public TaskController(TaskService taskService, FileService fileService) {

        this.taskService = taskService;
        this.fileService = fileService;
    }

    /**
     * This method gets all user's tasks.
     *
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
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
     * This method shows a definite user's task form by the task's id.
     *
     * @param httpSession the http session object.
     * @param model       the model object.
     * @param id          the definite task's id.
     * @return a html page via the result of the http request.
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
            List<FileDTO> userFiles = fileService.getAllTaskFiles(id);
            model.addAttribute("currentTask", currentTaskDTO);
            model.addAttribute("currentTaskFiles", userFiles);
            return "task";
        } catch (NoSuchElementException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks";
        }
    }

    /**
     * This method shows new task form.
     *
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
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
     * This method creates new user's task and save it in the database.
     *
     * @param task               the definite task's object.
     * @param binding            the binding result object.
     * @param model              the model object.
     * @param redirectAttributes the redirect attributes object.
     * @param httpSession        the http session object.
     * @return a html page via the result of the http request.
     */
    @PostMapping("/tasks/create")
    public String createNewTask(@Valid @ModelAttribute TaskDTO task,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("fileName") String fileArtificialName,
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
            FileDTO createdFile = fileService.uploadFile(file, createdTask.getTaskId(), fileArtificialName);
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
     * This method show a definite task's updating form by the task's id.
     *
     * @param id          the definite task's id.
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
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
     * This method updates a definite task by its own id.
     *
     * @param id                 the definite task's id.
     * @param task               the definite task object.
     * @param binding            the binding result object.
     * @param model              the model object.
     * @param redirectAttributes the redirect attributes object.
     * @param httpSession        the http session object.
     * @return a html page via the result of the http request.
     */
    @PostMapping("/tasks/update/{id}")
    public String updateTaskById(@PathVariable Long id,
                                 @Valid @ModelAttribute TaskDTO task,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("fileName") String fileArtificialName,
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
     * This method delete a definite task by its own id.
     *
     * @param id          the definite task's id.
     * @param httpSession the http session object.
     * @return a html page via the result of the http request.
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