package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

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
}
