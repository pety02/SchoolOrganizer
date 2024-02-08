package com.example.schoolorganizer.repository;

import java.util.*;

import com.example.schoolorganizer.model.NotebookSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotebookSectionRepository extends JpaRepository<NotebookSection, Long> {
    List<NotebookSection> findAllByAddedInNotebookNotebookId(Long id);
}