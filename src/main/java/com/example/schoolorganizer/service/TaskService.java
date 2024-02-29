package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.dto.TaskDTO;

public interface TaskService {
    List<TaskDTO> getAllTasksByUserId(Long id);

    Optional<TaskDTO> getUserTaskByTaskId(Long userId, Long taskId)
            throws NoSuchElementException;

    Optional<TaskDTO> createNewTask(TaskDTO taskDTO);

    Optional<TaskDTO> updateTaskById(Long id, TaskDTO taskDTO);

    void deleteTaskById(Long id);
}