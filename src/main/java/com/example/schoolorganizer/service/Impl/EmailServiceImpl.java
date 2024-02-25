package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * This class describes an EmailServiceImpl.
 *
 * @author Petya Licheva
 */
@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender emailSender;

    /**
     * General purpose constructor of EmailServiceImpl class.
     *
     * @param emailSender an email sender object.
     */
    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * This method sends emails from the application administration email to a
     * definite user's email with a definite subject and a definite message.
     *
     * @param to      the email's receiver.
     * @param subject the email's subject.
     * @param message the email's message.
     */
    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(to);
        emailMessage.setFrom("school_organizer@abv.bg");
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        emailSender.send(emailMessage);
    }
}