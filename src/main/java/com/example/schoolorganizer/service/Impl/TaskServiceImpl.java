package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.repository.FileRepository;
import com.example.schoolorganizer.repository.TaskRepository;
import com.example.schoolorganizer.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This class describes a TaskServiceImpl.
 *
 * @author Petya Licheva
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository tasksRepo;
    private final FileRepository fileRepo;
    private final IAdapter<Task, TaskDTO> taskAdapter;

    /**
     * General purpose constructor of TaskServiceImpl class.
     *
     * @param tasksRepo   the tasks repository object.
     * @param fileRepo
     * @param taskAdapter the task adapter object.
     */
    @Autowired
    public TaskServiceImpl(TaskRepository tasksRepo, FileRepository fileRepo, IAdapter<Task, TaskDTO> taskAdapter) {
        this.tasksRepo = tasksRepo;
        this.fileRepo = fileRepo;
        this.taskAdapter = taskAdapter;
    }

    /**
     * This method gets all tasks by their owner's id.
     *
     * @param id the owner's id.
     * @return a list of TaskDTO objects that represents
     * all tasks of definite user.
     */
    @Override
    public List<TaskDTO> getAllTasksByUserId(Long id) {
        List<Task> tasks = tasksRepo.getTasksByCreatedByUserId(id);
        List<TaskDTO> tasksDTOs = new ArrayList<>();
        for (var currentTask : tasks) {
            TaskDTO currentTaskDTO = taskAdapter.fromEntityToDTO(currentTask);
            tasksDTOs.add(currentTaskDTO);
        }

        return tasksDTOs;
    }

    /**
     * This method gets all unfinished user's tasks.
     *
     * @param id the id of the user.
     * @return a list of all unfinished user's tasks.
     */
    @Override
    public List<TaskDTO> getAllUnfinishedTasksByUserId(Long id) {
        List<Task> tasks = tasksRepo.getAllByFinishedNotAndCreatedBy_UserId(id);
        List<TaskDTO> tasksDTOs = new ArrayList<>();
        for (var currentTask : tasks) {
            TaskDTO currentTaskDTO = taskAdapter.fromEntityToDTO(currentTask);
            tasksDTOs.add(currentTaskDTO);
        }

        return tasksDTOs;
    }

    /**
     * This method gets a definite user's task by its
     * owner's id and its own id.
     *
     * @param userId the owner's id.
     * @param taskId the task's id.
     * @return an optional type of TaskDTO if in the database
     * there is a task with exact id and this owner's id.
     * @throws NoSuchElementException if there is a problem
     *                                with finding this definite task the method throws a
     *                                NoSuchElementException with an appropriate message.
     */
    @Override
    public Optional<TaskDTO> getUserTaskByTaskId(Long userId, Long taskId)
            throws NoSuchElementException {
        return Optional.of(taskAdapter.
                fromEntityToDTO(tasksRepo.
                        getTaskByCreatedByUserIdAndTaskId(userId, taskId)
                        .orElseThrow()));
    }

    /**
     * This method creates a new task with this method's
     * parameter's data.
     *
     * @param taskDTO the task dto object.
     * @return an optional type of TaskDTO if the task
     * is created successfully in the database and an
     * Optional.empty() object in all other situations.
     */
    @Transactional
    @Override
    public Optional<TaskDTO> createNewTask(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return Optional.empty();
        }
        if (taskDTO.getStartDate().isBefore(taskDTO.getFinishDate())) {
            Task task = taskAdapter.fromDTOToEntity(taskDTO);
            return Optional.of(taskAdapter.fromEntityToDTO(tasksRepo.save(task)));
        }
        return Optional.empty();
    }

    /**
     * This method updates a task with a definite id and with definite
     * data fields' values.
     *
     * @param id      the definite task's id.
     * @param taskDTO the fields' values to be updated with.
     * @return an optional type of TaskDTO if the task is
     * updated successfully in the database and an Optional.empty()
     * object in all other situations.
     */
    @Transactional
    @Override
    public Optional<TaskDTO> updateTaskById(Long id, TaskDTO taskDTO) {
        if (taskDTO == null) {
            return Optional.empty();
        }
        if (taskDTO.getStartDate().isBefore(taskDTO.getFinishDate())) {
            Task task = taskAdapter.fromDTOToEntity(taskDTO);
            if (tasksRepo.existsById(id)) {
                return Optional.of(taskAdapter.fromEntityToDTO(tasksRepo.save(task)));
            }
        }
        return Optional.empty();
    }

    /**
     * This method deletes a task with a definite id.
     *
     * @param id the definite task's id.
     */
    @Transactional
    @Override
    public void deleteTaskById(Long id) {
        if (tasksRepo.existsById(id)) {
            Task currentTask = tasksRepo.findById(id).orElseThrow();
            if (currentTask.getFiles() != null) {
                try {
                    List<File> filesList = currentTask.getFiles();
                    for (File currentFile : filesList) {
                        currentFile.setAddedInTasks(null);
                        java.io.File dirFile = new java.io.File(currentFile.getPath());

                        boolean isDeleted = dirFile.delete();
                        if (!isDeleted) {
                            throw new Exception("Problem with deleting a file from its absolute path.");
                        }
                        fileRepo.delete(currentFile);
                    }
                } catch (Exception ex) {
                    log.error(LocalDate.now() + ": //" + ex.getMessage());
                }
                tasksRepo.delete(currentTask);
            }
        }
    }
}