package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Custom query to find Client by email
    Client findByEmail(String email);

    // Method to find by reset token
    Client findByResetToken(String resetToken);

    // Method to check if an email already exists
    boolean existsByEmail(String email);
}
