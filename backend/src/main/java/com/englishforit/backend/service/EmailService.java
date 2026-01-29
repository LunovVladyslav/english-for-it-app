package com.englishforit.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendFeedbackEmail(String fromUser, String messageType, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        // In a real app, this should be a configured admin email
        message.setTo("admin@englishforit.com");
        message.setSubject("New Feedback: " + messageType);
        message.setText("From User: " + fromUser + "\n\n" + content);

        // We catch exception here to not break the flow if SMTP is not configured in
        // dev
        try {
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("Mock Email Sent: " + message.toString());
            // Log error but don't rethrow for MVP
            // e.printStackTrace();
        }
    }
}
