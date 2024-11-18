package com.example.semiautomatedlims.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
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

    // Existing methods
    public List<ClientReqForm> getFilteredRequests() {
        return clientReqFormRepository.findByStatusAndIsMicroBioTransferredFalseAndMicrobioTestsIsNotNull("For Testing");
    }

    public ClientReqForm getRequestDetailsById(Long clientReqid) {
        return clientReqFormRepository.findById(clientReqid).orElse(null);
    }

    public void saveRequest(ClientReqForm request) {
        clientReqFormRepository.save(request);
    }

    public void saveMicroBioData(MicroBioData microBioData) {
        microBioDataRepository.save(microBioData);
    }

    public List<MicroBioData> findMicroBioDataByLdControlNumber(String ldControlNumber) {
        return microBioDataRepository.findByLdControlNumber(ldControlNumber);
    }

    public List<ClientReqForm> findByMicrobioPending(String status, String microbioPending) {
        return clientReqFormRepository.findByStatusAndMicrobioPendingAndIsMicroBioTransferredTrue(status, microbioPending);
    }

    // New method to process microbiology data and map results to hard-coded test list
    public Map<String, String> getProcessedMicroBioData(String ldControlNumber, List<String> hardCodedTests) {
        List<MicroBioData> data = microBioDataRepository.findByLdControlNumber(ldControlNumber);
        Map<String, String> testResults = new HashMap<>();

        // Process each entry from the CSV of test names and map results
        for (MicroBioData entry : data) {
            String testNames = entry.getMicTestName(); // assuming micTestName is the CSV field

            // Split the test names by comma (CSV format)
            String[] tests = testNames.split(",");

            // Initialize "N/A" for each test
            for (String test : hardCodedTests) {
                // Check if test is present in the list from the entry
                if (containsTest(tests, test)) {
                    // Assuming the result is stored in micRemarks
                    testResults.put(test, entry.getMicRemarks() != null ? entry.getMicRemarks() : "N/A");
                } else {
                    testResults.put(test, "N/A");
                }
            }
        }
        return testResults;
    }

    // Helper method to check if a test exists in the list of tests
    private boolean containsTest(String[] tests, String testName) {
        for (String test : tests) {
            if (test.trim().equals(testName)) {
                return true;
            }
        }
        return false;
    }

    public List<MicroBioData> getAllMicroBioData() {
        return microBioDataRepository.findAll();
    }
}
