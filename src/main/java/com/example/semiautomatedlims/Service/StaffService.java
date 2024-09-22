package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder; 

    // Find staff by email
    public Staff findStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    // Save staff after updating the reset token and expiry time
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    // Find staff by reset token
    public Staff findByResetToken(String resetToken) {
        return staffRepository.findByResetToken(resetToken);
    }

    // Method to create a password reset token for a staff
    public boolean createPasswordResetTokenForStaff(String email, String token) {
        Staff staff = staffRepository.findByEmail(email);
        if (staff != null) {
            staff.setResetToken(token);
            staff.setTokenExpiry(LocalDateTime.now().plusMinutes(30));  // Token expires in 30 minutes
            staffRepository.save(staff);
            return true;
        }
        return false;
    }

    // Reset the staff's password
    // Example when resetting a password
    public boolean resetPassword(String email, String newPassword) {
        Staff staff = staffRepository.findByEmail(email);
        if (staff != null) {
            // Hash the new password before saving
            staff.setPassword(passwordEncoder.encode(newPassword));
            staffRepository.save(staff);
            return true;
        }
        return false;
    }
}
