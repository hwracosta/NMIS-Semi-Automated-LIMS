package com.example.semiautomatedlims.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MicroBioData; 
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;
import com.example.semiautomatedlims.Repository.MicroBioDataRepository; 

@Service
public class TestingMicrobioService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    @Autowired
    private MicroBioDataRepository microBioDataRepository;

    // Method to get requests by status
    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndIsMicroBioTransferredFalseAndMicrobioTestsIsNotNull("For Testing");
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
    public void saveMicroBioData(MicroBioData microBioData) {
        microBioDataRepository.save(microBioData);
    }

    // New method to retrieve MolBioData by LD control number
    public List<MicroBioData> findMicroBioDataByLdControlNumber(String ldControlNumber) {
        return microBioDataRepository.findByLdControlNumber(ldControlNumber);
    }
}
