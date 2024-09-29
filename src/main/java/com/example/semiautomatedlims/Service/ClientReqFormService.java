package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;

import java.util.List; 
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
}

