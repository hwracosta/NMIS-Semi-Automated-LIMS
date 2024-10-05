package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;

import com.example.semiautomatedlims.Repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    // Method to get requests by status
    public List<ClientReqForm> getRequestsByStatus(String status) {
        return releaseRepository.findByStatus(status);
    }

    // Method to find request by ID
    public ClientReqForm getRequestById(Long clientReqid) {
        Optional<ClientReqForm> requestOptional = releaseRepository.findById(clientReqid);
        return requestOptional.orElse(null);
    }

    // Method to update request status
    public void updateRequestStatus(ClientReqForm clientReqForm) {
        releaseRepository.save(clientReqForm);
    }

    // Method to get requests by multiple statuses
    public List<ClientReqForm> getProcessedRequests(List<String> statuses) {
        return releaseRepository.findByStatusIn(statuses);
    }
}
