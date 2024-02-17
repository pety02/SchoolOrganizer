package com.example.schoolorganizer.service;

public interface EmailService {
    void sendEmail(String to, String subject, String message);
}