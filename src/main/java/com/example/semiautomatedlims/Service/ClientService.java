package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Autowired PasswordEncoder

    // Method to find client by email
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // Add this method in your ClientService class
    public Client findByResetToken(String resetToken) {
        return clientRepository.findByResetToken(resetToken);
    }

    // Method to find client by reset token
    public Client findClientByResetToken(String token) { return clientRepository.findByResetToken(token); }

    // Method to save a new client
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    // Method to create a password reset token for a client
    public boolean createPasswordResetTokenForClient(String email, String token) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            client.setResetToken(token);
            client.setTokenExpiry(LocalDateTime.now().plusMinutes(30));  // Token expires in 30 minutes
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    // Method to reset the password
    public boolean resetPassword(String token, String newPassword) {
        Client client = clientRepository.findByResetToken(token);
        if (client != null && client.getTokenExpiry().isAfter(LocalDateTime.now())) {
            client.setPassword(passwordEncoder.encode(newPassword));  // Encode the new password
            client.setResetToken(null);  // Clear the token after reset
            clientRepository.save(client);
            return true;
        }
        return false;
    }
}
