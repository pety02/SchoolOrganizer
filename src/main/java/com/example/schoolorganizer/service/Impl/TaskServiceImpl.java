package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.repository.TaskRepository;
import com.example.schoolorganizer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository tasksRepo;
    private final IAdapter<Task, TaskDTO> taskDAO;

    @Autowired
    public TaskServiceImpl(TaskRepository tasksRepo, IAdapter<Task, TaskDTO> taskDAO) {
        this.tasksRepo = tasksRepo;
        this.taskDAO = taskDAO;
    }

    @Override
    public List<Task> getAllTasksByUserId(Long id) {
        return tasksRepo.getTasksByCreatedByUserId(id);
    }

    @Override
    public Optional<Task> getUserTaskByTaskId(Long userId, Long taskId) {
        return tasksRepo.getTaskByCreatedByUserIdAndTaskId(userId, taskId);
    }

    @Override
    public Optional<Task> createNewTask(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return Optional.empty();
        }
        if (taskDTO.getStartDate().isBefore(taskDTO.getFinishDate())) {
            Task task = taskDAO.fromDTOToEntity(taskDTO);
            return Optional.of(tasksRepo.save(task));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Task> updateTaskById(Long id, TaskDTO taskDTO) {
        if (taskDTO == null) {
            return Optional.empty();
        }
        if (taskDTO.getStartDate().isBefore(taskDTO.getFinishDate())) {
            Task task = taskDAO.fromDTOToEntity(taskDTO);
            if (tasksRepo.existsById(id)) {
                try {
                    return Optional.of(tasksRepo.save(task));
                } catch (NoSuchElementException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteTaskById(Long id) {
        tasksRepo.deleteById(id);
    }
}