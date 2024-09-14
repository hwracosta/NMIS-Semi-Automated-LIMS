package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class ClientFPWService {

    @Autowired
    private ClientRepository clientRepository;

    // Temporary storage for reset tokens
    private Map<String, String> resetTokens = new HashMap<>();
    private Map<String, Long> tokenExpiryTime = new HashMap<>(); // stores the expiry times

    // Generate a reset token and store it temporarily
    public String generateResetToken(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            String token = UUID.randomUUID().toString(); // Generate a unique token
            resetTokens.put(email, token);
            tokenExpiryTime.put(email, System.currentTimeMillis() + (15 * 60 * 1000)); // 15 minutes expiry
            return token;
        }
        return null;
    }

    // Verify if the reset token is valid
    public boolean verifyResetToken(String email, String token) {
        String storedToken = resetTokens.get(email);
        Long expiryTime = tokenExpiryTime.get(email);
        return storedToken != null && storedToken.equals(token) && System.currentTimeMillis() < expiryTime;
    }

    // Reset the client's password
    public boolean resetPassword(String email, String newPassword) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            client.setPassword(newPassword); // You might want to hash the password here
            clientRepository.save(client);
            return true;
        }
        return false;
    }
}
