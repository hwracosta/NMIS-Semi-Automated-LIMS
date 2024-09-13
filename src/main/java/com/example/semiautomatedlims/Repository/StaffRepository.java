package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    // Custom query to find a staff by email if needed (but JPA provides built-in methods like findById)
    Staff findByEmail(String email);
}
