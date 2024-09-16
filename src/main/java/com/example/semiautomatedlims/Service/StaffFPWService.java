package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private JavaMailSender mailSender;

    // Store reset codes and expiration times
    private final Map<String, PasswordResetData> passwordResetCodes = new HashMap<>();

    // Generate and send reset code to the staff's email
    public void sendPasswordResetCodeToEmail(String email) {
        Staff staff = staffRepository.findByEmail(email);

        // Log email lookup for debugging purposes
        System.out.println("Looking up staff with email: " + email);

        if (staff != null) {
            String resetCode = generateResetCode();  // Generate reset code
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);  // Code expires in 15 minutes
            passwordResetCodes.put(email, new PasswordResetData(resetCode, expirationTime));

            // Log to verify code generation and email preparation
            System.out.println("Generated reset code: " + resetCode + " for email: " + email);

            // Send the email
            String subject = "Your Password Reset Code";
            String content = "Your password reset code is: " + resetCode + ". It will expire in 15 minutes.";

            // Log before sending email
            System.out.println("Sending reset email to: " + email);
            sendEmail(email, subject, content);
        } else {
            // Log if no staff found for the email
            System.out.println("No staff found with email: " + email);
        }
    }

    // Verify the entered reset code
    public boolean verifyResetCode(String email, String resetCode) {
        PasswordResetData resetData = passwordResetCodes.get(email);

        // Log the verification attempt
        System.out.println("Verifying reset code: " + resetCode + " for email: " + email);

        if (resetData != null && resetData.resetCode().equals(resetCode)) {
            if (LocalDateTime.now().isBefore(resetData.expirationTime())) {
                passwordResetCodes.remove(email);  // Remove the code after successful verification

                // Log successful verification
                System.out.println("Code verified successfully for email: " + email);
                return true;
            } else {
                // Log code expiration
                System.out.println("Code expired for email: " + email);
            }
        } else {
            // Log invalid code or no data found
            System.out.println("Invalid code or no data found for email: " + email);
        }
        return false;
    }

    // Reset the staff's password
    public boolean resetPassword(String email, String newPassword) {
        Staff staff = staffRepository.findByEmail(email);

        // Log the password reset attempt
        System.out.println("Resetting password for email: " + email);

        if (staff != null) {
            staff.setPassword(newPassword); // Consider hashing the password before saving
            staffRepository.save(staff);

            // Log successful password reset
            System.out.println("Password reset successfully for email: " + email);
            return true;
        } else {
            // Log failure to find staff for password reset
            System.out.println("Failed to reset password: Staff not found for email: " + email);
        }
        return false;
    }

    // Generate a random 6-digit reset code
    private String generateResetCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);  // Generates a number between 100000 and 999999

        // Log code generation
        System.out.println("Generated 6-digit reset code: " + code);

        return String.valueOf(code);
    }

    // Send email using Spring's JavaMailSender
    private void sendEmail(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nmis.lims@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);

        // Log email sending attempt
        System.out.println("Attempting to send email to: " + toEmail);

        mailSender.send(message);

        // Log after email is sent
        System.out.println("Email sent to: " + toEmail);
    }

    // Inner class to store reset code and expiration time
    private record PasswordResetData(String resetCode, LocalDateTime expirationTime) {
    }
}