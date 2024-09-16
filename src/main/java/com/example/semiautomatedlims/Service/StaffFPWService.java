package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StaffFPWService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(StaffFPWService.class);
    private static final int EXPIRATION_MINUTES = 15;

    // Store reset codes and expiration times (using ConcurrentHashMap for thread safety)
    private final Map<String, PasswordResetData> passwordResetCodes = new ConcurrentHashMap<>();

    // Generate and send reset code to the staff's email
    public void sendPasswordResetCodeToEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.error("Invalid email address.");
            return;
        }

        Staff staff = staffRepository.findByEmail(email);
        logger.info("Looking up staff with email: {}", email);

        if (staff != null) {
            String resetCode = generateResetCode();
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
            passwordResetCodes.put(email, new PasswordResetData(resetCode, expirationTime));

            logger.info("Generated reset code: {} for email: {}", resetCode, email);

            String subject = "Your Password Reset Code";
            String content = "Your password reset code is: " + resetCode + ". It will expire in " + EXPIRATION_MINUTES + " minutes.";
            sendEmail(email, subject, content);
        } else {
            logger.warn("No staff found with email: {}", email);
        }
    }

    // Verify the entered reset code
    public boolean verifyResetCode(String email, String resetCode) {
        logger.info("Verifying reset code: {} for email: {}", resetCode, email);

        PasswordResetData resetData = passwordResetCodes.get(email);

        if (resetData != null && resetData.resetCode().equals(resetCode)) {
            if (LocalDateTime.now().isBefore(resetData.expirationTime())) {
                passwordResetCodes.remove(email);
                logger.info("Code verified successfully for email: {}", email);
                return true;
            } else {
                logger.warn("Code expired for email: {}", email);
            }
        } else {
            logger.warn("Invalid code or no data found for email: {}", email);
        }
        return false;
    }

    // Reset the staff's password
    public boolean resetPassword(String email, String newPassword) {
        Staff staff = staffRepository.findByEmail(email);
        logger.info("Resetting password for email: {}", email);

        if (staff != null) {
            staff.setPassword(newPassword);  // Use passwordEncoder.encode(newPassword) if you implement password encoding
            staffRepository.save(staff);
            logger.info("Password reset successfully for email: {}", email);
            return true;
        } else {
            logger.error("Failed to reset password: Staff not found for email: {}", email);
        }
        return false;
    }

    // Generate a random 6-digit reset code
    private String generateResetCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        logger.info("Generated 6-digit reset code: {}", code);
        return String.valueOf(code);
    }

    // Send email using Spring's JavaMailSender
    private void sendEmail(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nmis.lims@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);

        logger.info("Attempting to send email to: {}", toEmail);
        mailSender.send(message);
        logger.info("Email sent to: {}", toEmail);
    }

    // Inner class to store reset code and expiration time
    private record PasswordResetData(String resetCode, LocalDateTime expirationTime) {
    }
}
