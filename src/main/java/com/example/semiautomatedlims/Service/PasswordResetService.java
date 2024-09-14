package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Repository.ClientRepository;
import com.example.semiautomatedlims.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private EmailService emailService;

    // Generate reset token for Client
    public boolean generateClientResetToken(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            String token = UUID.randomUUID().toString();
            client.setResetToken(token);
            client.setTokenExpiry(LocalDateTime.now().plusMinutes(15)); // 15 minutes expiry
            clientRepository.save(client);

            // Send email with reset token
            String subject = "Client Password Reset";
            String content = "Use the following link to reset your password: " +
                    "http://yourdomain.com/reset-password?token=" + token;
            emailService.sendEmail(client.getEmail(), subject, content);

            return true;
        }
        return false;
    }

    // Generate reset token for Staff
    public boolean generateStaffResetToken(String email) {
        Staff staff = staffRepository.findByEmail(email);
        if (staff != null) {
            String token = UUID.randomUUID().toString();
            staff.setResetToken(token);
            staff.setTokenExpiry(LocalDateTime.now().plusMinutes(15)); // 15 minutes expiry
            staffRepository.save(staff);

            // Send email with reset token
            String subject = "Staff Password Reset";
            String content = "Use the following link to reset your password: " +
                    "http://yourdomain.com/reset-password?token=" + token;
            emailService.sendEmail(staff.getEmail(), subject, content);

            return true;
        }
        return false;
    }

    public boolean validateClientResetToken(String token) {
        Client client = clientRepository.findByResetToken(token);
        if (client != null && client.getTokenExpiry().isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    // Validate Staff reset token
    public boolean validateStaffResetToken(String token) {
        Staff staff = staffRepository.findByResetToken(token);
        if (staff != null && staff.getTokenExpiry().isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
