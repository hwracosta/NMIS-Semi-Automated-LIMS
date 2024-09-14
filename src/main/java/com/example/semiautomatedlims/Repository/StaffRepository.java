package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);

    // Find by reset token
    Staff findByResetToken(String resetToken);
}
