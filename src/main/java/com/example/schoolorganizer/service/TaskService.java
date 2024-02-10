package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.Task;
import jakarta.transaction.Transactional;

public interface TaskService {
    List<TaskDTO> getAllTasksByUserId(Long id);

    Optional<TaskDTO> getUserTaskByTaskId(Long userId, Long taskId);

    Optional<TaskDTO> createNewTask(TaskDTO taskDTO);

    Optional<TaskDTO> updateTaskById(Long id, TaskDTO taskDTO);

    void deleteTaskById(Long id);
}