package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.CalendarEvent;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.repository.CalendarEventRepository;
import com.example.schoolorganizer.repository.UserRepository;
import com.example.schoolorganizer.service.CalendarEventService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarEventServiceImpl implements CalendarEventService {
    private final CalendarEventRepository eventRepository;
    private final UserRepository userRepo;
    private final IAdapter<CalendarEvent, CalendarEventDTO> calendarEventAdapter;
    private final IAdapter<User, UserDTO> userAdapter;

    @Autowired
    public CalendarEventServiceImpl(CalendarEventRepository eventRepository, UserRepository userRepo, IAdapter<CalendarEvent, CalendarEventDTO> calendarEventAdapter, IAdapter<User, UserDTO> userAdapter) {
        this.eventRepository = eventRepository;
        this.userRepo = userRepo;
        this.calendarEventAdapter = calendarEventAdapter;
        this.userAdapter = userAdapter;
    }

    @Override
    public List<CalendarEvent> getAllEventsByUserId(Long id) {
        return eventRepository.findAllByCreatedByUserId(id);
    }

    @Transactional
    @Override
    public Optional<CalendarEvent> createEventByUserId(Long id, CalendarEventDTO eventDTO) {
        CalendarEvent event = calendarEventAdapter.fromDTOToEntity(eventDTO);
        User createdBy = userRepo.findByUserId(id).orElseThrow();
        event.setCreatedBy(createdBy);
        return Optional.of(eventRepository.save(event));
    }
}
