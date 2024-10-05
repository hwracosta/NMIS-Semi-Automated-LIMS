package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.Client; // Import Client entity
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientReqFormService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    public List<ClientReqForm> getAllClientRequests() {
        return clientReqFormRepository.findAll();
    }

    public void saveClientReqForm(ClientReqForm clientReqForm) {
        clientReqFormRepository.save(clientReqForm);
    }

    // Updated method to find requests by Client
    public List<ClientReqForm> getRequestsByClient(Client client) {
        return clientReqFormRepository.findByClient(client);
    }
    
    public ClientReqForm findRequestById(Long clientReqid) {
        Optional<ClientReqForm> requestOptional = clientReqFormRepository.findById(clientReqid);
        return requestOptional.isPresent() ? requestOptional.get() : null;
    }
}