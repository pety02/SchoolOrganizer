package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.model.CalendarEvent;

import java.util.*;

public interface CalendarEventService {
    List<CalendarEvent> getAllEventsByUserId(Long id);

    Optional<CalendarEvent> createEventByUserId(Long id, CalendarEventDTO eventDTO);
}