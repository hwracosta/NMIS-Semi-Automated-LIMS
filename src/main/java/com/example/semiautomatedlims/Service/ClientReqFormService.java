package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientReqFormService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    public void saveClientReqForm(ClientReqForm clientReqForm) {
        clientReqFormRepository.save(clientReqForm);
    }
}
