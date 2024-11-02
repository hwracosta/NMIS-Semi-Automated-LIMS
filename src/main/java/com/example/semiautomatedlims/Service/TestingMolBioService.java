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

    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndIsMolBioTransferredFalseAndMolecTestsIsNotNull("For Testing");
    }

    public ClientReqForm getRequestDetailsById(Long clientReqid) {
        return clientReqFormRepository.findById(clientReqid).orElse(null);
    }

    public void saveRequest(ClientReqForm request) {
        clientReqFormRepository.save(request);
    }

    public void saveMolBioData(MolBioData molBioData) {
        molBioDataRepository.save(molBioData);
    }

    public List<MolBioData> findMolBioDataByLdControlNumber(String ldControlNumber) {
        return molBioDataRepository.findByLdControlNumber(ldControlNumber);
    }

    public List<ClientReqForm> findByMolbioPending(String status, String molbioPending) {
        return clientReqFormRepository.findByStatusAndMolbioPendingAndIsMolBioTransferredTrue(status, molbioPending);
    }
}
