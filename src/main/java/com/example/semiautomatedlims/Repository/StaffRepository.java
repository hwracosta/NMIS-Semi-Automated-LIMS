package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    // Custom query to find Staff by email
    Staff findByEmail(String email);

    // Custom query to find Staff by reset token
    Staff findByResetToken(String resetToken);
}
