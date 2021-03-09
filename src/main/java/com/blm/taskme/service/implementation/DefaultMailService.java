package com.blm.taskme.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultMailService {
    @Value("${mail.sender-email}")
    private String senderEmail;
    private final JavaMailSender mailSender;

    @Autowired
    public DefaultMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(String emailTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
