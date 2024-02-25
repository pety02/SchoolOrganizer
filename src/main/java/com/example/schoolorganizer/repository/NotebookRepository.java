package com.example.schoolorganizer.repository;

import java.util.*;

import com.example.schoolorganizer.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface describes NotebookRepository with its functionality.
 *
 * @author Petya Licheva
 */
@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    List<Notebook> findAllByCreatedByUserId(Long id);

    Optional<Notebook> findByCreatedByUserIdAndNotebookId(Long userId, Long notebookId);
}