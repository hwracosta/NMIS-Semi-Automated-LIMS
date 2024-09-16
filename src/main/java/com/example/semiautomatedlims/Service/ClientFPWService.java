package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ClientFPWService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Store reset codes and expiration times for clients
    private Map<String, PasswordResetData> passwordResetCodes = new HashMap<>();
    private Client verifiedClient;  // Store the verified client object here

    // Generate and send reset code to the client's email
    public void sendPasswordResetCodeToEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            String code = generateResetCode();
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);  // Code expires in 15 minutes
            passwordResetCodes.put(code, new PasswordResetData(code, expirationTime, client));  // Store with code

            // Send the email
            String subject = "Your Password Reset Code";
            String content = "Your password reset code is: " + code + ". It will expire in 15 minutes.";
            sendEmail(email, subject, content);
        }
    }

    // Verify the entered reset code and save the corresponding client
    public boolean verifyResetCode(String code) {
        PasswordResetData resetData = passwordResetCodes.get(code);
        if (resetData != null && LocalDateTime.now().isBefore(resetData.getExpirationTime())) {
            verifiedClient = resetData.getClient();  // Save the verified client
            passwordResetCodes.remove(code);  // Remove the code after verification
            return true;
        }
        return false;
    }

    // Reset the client's password after code verification
    public boolean resetPassword(String newPassword) {
        if (verifiedClient != null) {
            verifiedClient.setPassword(newPassword); // Ensure password encoding happens here
            clientRepository.save(verifiedClient);  // Save the client with the new password
            verifiedClient = null;  // Clear after reset
            return true;
        }
        return false;
    }

    // Generate a random 6-digit reset code
    private String generateResetCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);  // Generates a number between 100000 and 999999
        return String.valueOf(code);
    }

    // Send email using Spring's JavaMailSender
    private void sendEmail(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nmis.lims@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    // Inner class to store reset code, expiration time, and client information
    private static class PasswordResetData {
        private String code;
        private LocalDateTime expirationTime;
        private Client client;

        public PasswordResetData(String code, LocalDateTime expirationTime, Client client) {
            this.code = code;
            this.expirationTime = expirationTime;
            this.client = client;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getExpirationTime() {
            return expirationTime;
        }

        public Client getClient() {
            return client;
        }
    }
}
