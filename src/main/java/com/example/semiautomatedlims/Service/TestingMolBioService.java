package com.example.semiautomatedlims.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Ensure you have this repository
import org.springframework.stereotype.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;

@Service
public class TestingMolBioService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository; // Inject your repository

    // Method to get requests by status
    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndMolecTestsIsNotNull("For Testing");
    }

    // Method to get specific request details by clientReqid
    public ClientReqForm getRequestDetailsById(Long clientReqid) {
        return clientReqFormRepository.findById(clientReqid)
                .orElse(null); // Return null if not found
    }
}
