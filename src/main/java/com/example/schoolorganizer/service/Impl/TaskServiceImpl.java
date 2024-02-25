package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.Task;
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
 *
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository tasksRepo;
    private final IAdapter<Task, TaskDTO> taskDAO;

    /**
     * @param tasksRepo
     * @param taskDAO
     */
    @Autowired
    public TaskServiceImpl(TaskRepository tasksRepo, IAdapter<Task, TaskDTO> taskDAO) {
        this.tasksRepo = tasksRepo;
        this.taskDAO = taskDAO;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<TaskDTO> getAllTasksByUserId(Long id) {
        List<Task> tasks = tasksRepo.getTasksByCreatedByUserId(id);
        List<TaskDTO> tasksDTOs = new ArrayList<>();
        for (var currentTask : tasks) {
            TaskDTO currentTaskDTO = taskDAO.fromEntityToDTO(currentTask);
            tasksDTOs.add(currentTaskDTO);
        }

        return tasksDTOs;
    }

    /**
     * @param userId
     * @param taskId
     * @return
     */
    @Override
    public Optional<TaskDTO> getUserTaskByTaskId(Long userId, Long taskId) {
        return Optional.of(taskDAO.
                fromEntityToDTO(tasksRepo.
                        getTaskByCreatedByUserIdAndTaskId(userId, taskId)
                        .orElseThrow()));
    }

    /**
     * @param taskDTO
     * @return
     */
    @Transactional
    @Override
    public Optional<TaskDTO> createNewTask(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return Optional.empty();
        }
        if (taskDTO.getStartDate().isBefore(taskDTO.getFinishDate())) {
            Task task = taskDAO.fromDTOToEntity(taskDTO);
            return Optional.of(taskDAO.fromEntityToDTO(tasksRepo.save(task)));
        }
        return Optional.empty();
    }

    /**
     * @param id
     * @param taskDTO
     * @return
     */
    @Transactional
    @Override
    public Optional<TaskDTO> updateTaskById(Long id, TaskDTO taskDTO) {
        if (taskDTO == null) {
            return Optional.empty();
        }
        if (taskDTO.getStartDate().isBefore(taskDTO.getFinishDate())) {
            Task task = taskDAO.fromDTOToEntity(taskDTO);
            if (tasksRepo.existsById(id)) {
                try {
                    return Optional.of(taskDAO.fromEntityToDTO(tasksRepo.save(task)));
                } catch (NoSuchElementException e) {
                    log.error(LocalDate.now() + ": " + e.getMessage());
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    /**
     * @param id
     */
    @Transactional
    @Override
    public void deleteTaskById(Long id) {
        tasksRepo.deleteById(id);
    }
}