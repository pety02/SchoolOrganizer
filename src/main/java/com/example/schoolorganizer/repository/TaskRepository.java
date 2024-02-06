package com.example.schoolorganizer.repository;

import java.util.*;

import com.example.schoolorganizer.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByCreatedByUserId(Long id);

    Optional<Task> getTaskByCreatedByUserIdAndTaskId(Long userId, Long id);
}