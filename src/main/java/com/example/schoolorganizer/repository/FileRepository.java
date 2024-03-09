package com.example.schoolorganizer.repository;

import java.util.Optional;

import com.example.schoolorganizer.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface describes FileRepository with its functionality.
 *
 * @author Petya Licheva
 */
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByName(String name);

    @Query(value = "SELECT * FROM File WHERE file_id IN (SELECT files_file_id FROM (SELECT * FROM Task_Files tf WHERE tf.task_task_id = :id))", nativeQuery = true)
    List<File> findAllByAddedInTask(@Param("id") Long taskId);
}