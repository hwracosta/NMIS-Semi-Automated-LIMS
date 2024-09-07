package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Method to find client by email
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // Method to save a new client
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
