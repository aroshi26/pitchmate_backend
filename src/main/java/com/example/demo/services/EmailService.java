package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTemporaryPassword(String recipientEmail, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Temporary Password for PitchMate");
        message.setText("Hello,\n\nYou have been added as staff in PitchMate.\n\n"
                + "Your temporary login credentials are:\n"
                + "Email: " + recipientEmail + "\n"
                + "Temporary Password: " + tempPassword + "\n\n"
                + "Please change your password upon first login.\n\nBest Regards,\nPitchMate Team");

        mailSender.send(message);
        System.out.println("📧 Email sent to " + recipientEmail);
    }
}
