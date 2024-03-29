package com.example.schoolorganizer.repository;

import java.util.*;

import com.example.schoolorganizer.model.NotebookSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface describes NotebookSectionRepository with its functionality.
 *
 * @author Petya Licheva
 */
@Repository
public interface NotebookSectionRepository extends JpaRepository<NotebookSection, Long> {
    List<NotebookSection> findAllByAddedInNotebookNotebookId(Long id);

    Optional<NotebookSection> findByAddedInNotebook_NotebookIdAndNotebookSectionId(Long notebookId, Long sectionId);
}