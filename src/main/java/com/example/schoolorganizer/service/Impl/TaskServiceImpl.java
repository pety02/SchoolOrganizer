package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.repository.TaskRepository;
import com.example.schoolorganizer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskServiceImpl implements TaskService {
    private final TaskRepository tasksRepo;

    @Autowired
    public TaskServiceImpl(TaskRepository tasksRepo) {
        this.tasksRepo = tasksRepo;
    }

    @Override
    public List<Task> getAllTasksByUserId(Long id) {
        return tasksRepo.getTasksByCreatedByUserId(id);
    }

    @Override
    public Optional<Task> getUserTaskByTaskId(Long userIdd, int taskId) {
        return Optional.empty();
    }
}