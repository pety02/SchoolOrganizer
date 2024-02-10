package com.example.schoolorganizer.repository;

import java.util.*;

import com.example.schoolorganizer.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findAllByCreatedByUserId(Long id);
}