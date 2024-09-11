package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // Find a staff by their email
    public Staff findStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    // Save staff to the database (useful for registration)
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }
}
