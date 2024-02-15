package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.CalendarEventDTO;

import java.util.*;

public interface CalendarEventService {
    List<CalendarEventDTO> getAllEventsByUserId(Long id);

    Optional<CalendarEventDTO> createEventByUserId(Long id, CalendarEventDTO eventDTO);

    void deleteEventsByTitle(String title, Long id);
}