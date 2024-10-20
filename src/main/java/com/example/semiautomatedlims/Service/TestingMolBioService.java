package com.example.semiautomatedlims.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MolBioData; 
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;
import com.example.semiautomatedlims.Repository.MolBioDataRepository; 

@Service
public class TestingMolBioService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    @Autowired
    private MolBioDataRepository molBioDataRepository;

    // Method to get requests by status
    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndIsMolBioTransferredFalseAndMolecTestsIsNotNull("For Testing");
    }    

    // Method to get specific request details by clientReqid
    public ClientReqForm getRequestDetailsById(Long clientReqid) {
        return clientReqFormRepository.findById(clientReqid)
                .orElse(null); // Return null if not found
    }

    public void saveRequest(ClientReqForm request) {
        clientReqFormRepository.save(request);
    }

    // New method to save MolBioData
    public void saveMolBioData(MolBioData molBioData) {
        molBioDataRepository.save(molBioData);
    }

    // New method to retrieve MolBioData by LD control number
    public List<MolBioData> findMolBioDataByLdControlNumber(String ldControlNumber) {
        return molBioDataRepository.findByLdControlNumber(ldControlNumber);
    }

    // Method to get requests by status and molbio_pending as 'pending'
    public List<ClientReqForm> findByMolbioPending(String status, String molbioPending) {
        return clientReqFormRepository.findByStatusAndMolbioPending(status, molbioPending);
    }
}
