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

    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndIsChemTransferredFalseAndChemTestsIsNotNull("For Testing");
    }

    public ClientReqForm getRequestDetailsById(Long clientReqid) {
        return clientReqFormRepository.findById(clientReqid).orElse(null);
    }

    public void saveRequest(ClientReqForm request) {
        clientReqFormRepository.save(request);
    }

    public void saveChemData(ChemData chemData) {
        chemDataRepository.save(chemData);
    }

    public List<ChemData> findChemDataByLdControlNumber(String ldControlNumber) {
        return chemDataRepository.findByLdControlNumber(ldControlNumber);
    }

    public List<ClientReqForm> findByChemPending(String status, String chemPending) {
        return clientReqFormRepository.findByStatusAndChemPendingAndIsChemTransferredTrue(status, chemPending);
    }
}
