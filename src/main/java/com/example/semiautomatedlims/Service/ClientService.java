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

    public boolean isEmailInUse(String email) {
        return clientRepository.existsByEmail(email);
    }    

    // Method to find client by reset token
    public Client findByResetToken(String resetToken) {  // Ensure the name matches in both files
        return clientRepository.findByResetToken(resetToken);
    }

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

    // Reset the client's password
// Example when resetting a password
    public boolean resetPassword(String email, String newPassword) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            // Hash the new password before saving
            client.setPassword(passwordEncoder.encode(newPassword));
            clientRepository.save(client);
            return true;
        }
        return false;
    }
}