package com.example.semiautomatedlims.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;

@Service
public class TestingChemService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    // Method to get requests by status
    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndTransferredIsFalseAndChemTestsIsNotNull("For Testing");
    }    

    // Method to get specific request details by clientReqid
    public ClientReqForm getRequestDetailsById(Long clientReqid) {
        return clientReqFormRepository.findById(clientReqid)
                .orElse(null); 
    }

    public void saveRequest(ClientReqForm request) {
        clientReqFormRepository.save(request);
    }
}
