package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.model.CalendarEvent;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.repository.CalendarEventRepository;
import com.example.schoolorganizer.repository.UserRepository;
import com.example.schoolorganizer.service.CalendarEventService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarEventServiceImpl implements CalendarEventService {
    private final CalendarEventRepository eventRepository;
    private final UserRepository userRepo;
    private final IAdapter<CalendarEvent, CalendarEventDTO> calendarEventAdapter;

    @Autowired
    public CalendarEventServiceImpl(CalendarEventRepository eventRepository,
                                    UserRepository userRepo,
                                    IAdapter<CalendarEvent, CalendarEventDTO>
                                            calendarEventAdapter) {
        this.eventRepository = eventRepository;
        this.userRepo = userRepo;
        this.calendarEventAdapter = calendarEventAdapter;
    }

    @Override
    public List<CalendarEventDTO> getAllEventsByUserId(Long id) {
        List<CalendarEvent> events = eventRepository.findAllByCreatedByUserId(id);
        List<CalendarEventDTO> eventsDTOs = new ArrayList<>();
        for (var currentEvent : events) {
            CalendarEventDTO currentEventDTO = calendarEventAdapter.fromEntityToDTO(currentEvent);
            eventsDTOs.add(currentEventDTO);
        }

        return eventsDTOs;
    }

    @Transactional
    @Override
    public Optional<CalendarEventDTO> createEventByUserId(Long id, CalendarEventDTO eventDTO) {
        if (eventDTO.getEndDate().isBefore(eventDTO.getStartDate())) {
            throw new IllegalArgumentException("The start date of the event should be before the end date.");
        }
        CalendarEvent event = calendarEventAdapter.fromDTOToEntity(eventDTO);
        User createdBy = userRepo.findByUserId(id).orElseThrow();
        event.setCreatedBy(createdBy);
        return Optional.of(calendarEventAdapter
                .fromEntityToDTO(eventRepository.save(event)));
    }

    @Override
    public List<CalendarEventDTO> searchByTitle(Long id, String title) {
        List<CalendarEvent> events = eventRepository.findAllByCreatedByUserIdAndTitle(id, title);
        List<CalendarEventDTO> eventsDTOs = new ArrayList<>();
        for (CalendarEvent currentEvent : events) {
            eventsDTOs.add(calendarEventAdapter.fromEntityToDTO(currentEvent));
            System.out.println(currentEvent.getTitle());
        }

        return eventsDTOs;
    }

    @Override
    public void deleteEventsByTitle(String title, Long id) {
        List<CalendarEvent> events = eventRepository.findAllByCreatedByUserIdAndTitle(id, title);
        eventRepository.deleteAll(events);
    }

    @Override
    public void deleteByID(Long id) {
        eventRepository.deleteById(id);
    }
}