package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.Task;
import jakarta.transaction.Transactional;

public interface TaskService {
    List<Task> getAllTasksByUserId(Long id);

    Optional<Task> getUserTaskByTaskId(Long userId, Long taskId);

    Optional<Task> createNewTask(TaskDTO taskDTO);

    Optional<Task> updateTaskById(Long id, TaskDTO taskDTO);

    void deleteTaskById(Long id);
}