package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarEventId;
    @Column(nullable = false, length = 500)
    private String title;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column(length = 200)
    private String color;
    @ManyToOne
    private User createdBy;

    public CalendarEvent() {
    }

    public CalendarEvent(String title, LocalDate startDate, LocalDate endDate, String color, User createdBy) {
        setTitle(title);
        setStartDate(startDate);
        setEndDate(endDate);
        setColor(color);
        setCreatedBy(createdBy);
    }

    public Long getCalendarEventId() {
        return calendarEventId;
    }

    public void setCalendarEventId(Long calendarEventId) {
        this.calendarEventId = calendarEventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}