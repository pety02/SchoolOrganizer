package com.example.schoolorganizer.repository;

import java.util.*;

import com.example.schoolorganizer.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * This interface describes CalendarEventRepository with its functionality.
 *
 * @author Petya Licheva
 */
@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findAllByCreatedByUserId(Long id);

    List<CalendarEvent> findAllByCreatedByUserIdAndTitle(Long id, String title);
}