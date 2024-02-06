package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.model.Task;

public interface TaskService {
    List<Task> getAllTasksByUserId(Long id);

    Optional<Task> getUserTaskByTaskId(Long userId, int taskId);
}