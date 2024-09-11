package com.example.semiautomatedlims.Service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class Staff2FAService {

    private Map<String, String> staff2FACodes = new HashMap<>(); // To store the 2FA codes temporarily

    // Generate and send 2FA code to the staff's email
    public boolean send2FACode(String email) {
        String code = generate2FACode();
        staff2FACodes.put(email, code);
        // Logic to send the email (use an email service)
        return sendEmail(email, code);
    }

    // Verify the entered 2FA code
    public boolean verify2FACode(String email, String code) {
        String storedCode = staff2FACodes.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            staff2FACodes.remove(email); // Remove code after verification
            return true;
        }
        return false;
    }

    // Generate a random 6-digit 2FA code
    private String generate2FACode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(code);
    }

    // Mock email sending logic (replace with real email service)
    private boolean sendEmail(String email, String code) {
        // Implement real email sending logic here
        System.out.println("Sending 2FA code " + code + " to " + email);
        return true;
    }
}
