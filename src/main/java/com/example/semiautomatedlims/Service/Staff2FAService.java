package com.example.semiautomatedlims.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class Staff2FAService {

    @Autowired
    private JavaMailSender mailSender;

    // Store 2FA codes and expiration times
    private Map<String, TwoFAData> staff2FACodes = new HashMap<>();  

    // Generate and send 2FA code to the staff's email
    public void sendTwoFactorCodeToEmail(String email) {
        String code = generate2FACode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);  // 2FA code expires in 15 minutes
        staff2FACodes.put(email, new TwoFAData(code, expirationTime));

        // Send the email
        String subject = "Your 2FA Code";
        String content = "Your 2FA code is: " + code + ". It will expire in 15 minutes.";
        sendEmail(email, subject, content);
    }

    // Verify the entered 2FA code
    public boolean verify2FACode(String email, String code) {
        TwoFAData twoFAData = staff2FACodes.get(email);
        if (twoFAData != null && twoFAData.getCode().equals(code)) {
            if (LocalDateTime.now().isBefore(twoFAData.getExpirationTime())) {
                staff2FACodes.remove(email);  // Remove the code after successful verification
                return true;
            }
        }
        return false;
    }

    // Generate a random 6-digit 2FA code
    private String generate2FACode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);  // Generates a number between 100000 and 999999
        return String.valueOf(code);
    }

    // Send email using Spring's JavaMailSender
    private void sendEmail(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
