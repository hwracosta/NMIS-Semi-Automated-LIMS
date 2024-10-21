package com.example.semiautomatedlims.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ChemDataRepository;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;

@Service
public class TestingChemService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    @Autowired
    private ChemDataRepository chemDataRepository;

    // Method to get requests that are in "For Testing" status and have not been transferred
    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndIsChemTransferredFalseAndChemTestsIsNotNull("For Testing");
    }

    // Method to get specific request details by clientReqid
    public ClientReqForm getRequestDetailsById(Long clientReqid) {
        return clientReqFormRepository.findById(clientReqid).orElse(null);
    }

    // Method to save ClientReqForm
    public void saveRequest(ClientReqForm request) {
        clientReqFormRepository.save(request);
    }

    // New method to save ChemData
    public void saveChemData(ChemData chemData) {
        chemDataRepository.save(chemData);
    }

    // New method to retrieve ChemData by LD control number
    public List<ChemData> findChemDataByLdControlNumber(String ldControlNumber) {
        return chemDataRepository.findByLdControlNumber(ldControlNumber);
    }

    // Fetch requests with "For Testing", chem_pending as "pending", and transferred
    public List<ClientReqForm> findByChemPending(String status, String chemPending) {
        return clientReqFormRepository.findByStatusAndChemPendingAndIsChemTransferredTrue(status, chemPending);
    }
}
