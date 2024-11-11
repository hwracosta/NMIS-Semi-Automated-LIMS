package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportReleaseService {

    private final ClientReqFormRepository clientReqFormRepository;

    @Autowired
    public ReportReleaseService(ClientReqFormRepository clientReqFormRepository) {
        this.clientReqFormRepository = clientReqFormRepository;
    }

    // Get all requests where the status is 'Complete'
    public List<ClientReqForm> getCompletedRequests() {
        return clientReqFormRepository.findByStatus("Complete");
    }
}
