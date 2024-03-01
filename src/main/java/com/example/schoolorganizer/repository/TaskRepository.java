package com.example.schoolorganizer.repository;

import java.util.*;

import com.example.schoolorganizer.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * This interface describes TaskRepository with its functionality.
 *
 * @author Petya Licheva
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByCreatedByUserId(Long id);

    @Query(value = "SELECT * FROM Task t WHERE t.is_finished = false AND t.created_by_user_id = :id", nativeQuery = true)
    List<Task> getAllByFinishedNotAndCreatedBy_UserId(@Param("id") Long id);

    Optional<Task> getTaskByCreatedByUserIdAndTaskId(Long userId, Long id);
}