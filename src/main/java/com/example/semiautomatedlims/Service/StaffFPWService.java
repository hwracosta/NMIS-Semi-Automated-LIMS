package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class StaffFPWService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    // Store reset codes and expiration times for staff
    private final Map<String, PasswordResetData> passwordResetCodes = new HashMap<>();

    // Generate and send reset code to the staff's email
    public void sendPasswordResetCodeToEmail(String email) {
        Staff staff = staffRepository.findByEmail(email);
        if (staff != null) {
            String code = generateResetCode();
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);  // Code expires in 15 minutes
            passwordResetCodes.put(email, new PasswordResetData(code, expirationTime));

            // Send the email
            String subject = "Your Password Reset Code";
            String content = "Your password reset code is: " + code + ". It will expire in 15 minutes.";
            sendEmail(email, subject, content);
        }
    }

    // Verify the entered reset code
    public boolean verifyResetCode(String code) {
        for (Map.Entry<String, PasswordResetData> entry : passwordResetCodes.entrySet()) {
            PasswordResetData resetData = entry.getValue();
            if (resetData.getCode().equals(code)) {
                if (LocalDateTime.now().isBefore(resetData.getExpirationTime())) {
                    passwordResetCodes.remove(entry.getKey());  // Remove the code after verification
                    return true;
                }
            }
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

    public String getEmailByCode(String code) {
        for (Map.Entry<String, PasswordResetData> entry : passwordResetCodes.entrySet()) {
            if (entry.getValue().getCode().equals(code)) {
                return entry.getKey();  // Return the email associated with the code
            }
        }
        return null;  // Return null if no match found
    }

    // Inner class to store reset code and expiration time
    private static class PasswordResetData {
        private String code;
        private LocalDateTime expirationTime;

        public PasswordResetData(String code, LocalDateTime expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getExpirationTime() {
            return expirationTime;
        }
    }
}