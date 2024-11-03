package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Entity.MolBioData;
import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;
import com.example.semiautomatedlims.Repository.MolBioDataRepository;
import com.example.semiautomatedlims.Repository.ChemDataRepository;
import com.example.semiautomatedlims.Repository.MicroBioDataRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientReqFormService {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    @Autowired
    private MolBioDataRepository molBioDataRepository;

    @Autowired
    private ChemDataRepository chemDataRepository;

    @Autowired
    private MicroBioDataRepository microBioDataRepository;

    public List<ClientReqForm> getAllClientRequests() {
        return clientReqFormRepository.findAll();
    }

    public void saveClientReqForm(ClientReqForm clientReqForm) {
        clientReqFormRepository.save(clientReqForm);
    }

    public List<ClientReqForm> getRequestsByClient(Client client) {
        return clientReqFormRepository.findByClient(client);
    }

    public ClientReqForm findRequestById(Long clientReqid) {
        Optional<ClientReqForm> requestOptional = clientReqFormRepository.findById(clientReqid);
        return requestOptional.orElse(null);
    }

    public Optional<ClientReqForm> findById(Long id) {
        return clientReqFormRepository.findById(id);
    }

    // Method to retrieve the LD Control Number
    public String getLdControlNumber(Long clientReqid) {
        ClientReqForm clientReqForm = findRequestById(clientReqid);
        return clientReqForm != null ? clientReqForm.getLdControlNumber() : "Unknown";
    }

    // Method to find MolBioData by LD Control Number
    public List<MolBioData> findMolBioDataByLdControlNumber(String ldControlNumber) {
        return molBioDataRepository.findByLdControlNumber(ldControlNumber);
    }

    // Method to find ChemData by LD Control Number
    public List<ChemData> findChemDataByLdControlNumber(String ldControlNumber) {
        return chemDataRepository.findByLdControlNumber(ldControlNumber);
    }

    // Method to find MicroBioData by LD Control Number
    public List<MicroBioData> findMicroBioDataByLdControlNumber(String ldControlNumber) {
        return microBioDataRepository.findByLdControlNumber(ldControlNumber);
    }
}
