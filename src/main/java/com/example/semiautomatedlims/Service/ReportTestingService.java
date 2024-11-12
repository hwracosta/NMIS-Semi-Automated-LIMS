package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Entity.MolBioData;
import com.example.semiautomatedlims.ReportSummaryDTO;
import com.example.semiautomatedlims.Repository.ChemDataRepository;
import com.example.semiautomatedlims.Repository.MicroBioDataRepository;
import com.example.semiautomatedlims.Repository.MolBioDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportTestingService {

    private final ChemDataRepository chemDataRepository;
    private final MicroBioDataRepository microBioDataRepository;
    private final MolBioDataRepository molBioDataRepository;

    @Autowired
    public ReportTestingService(ChemDataRepository chemDataRepository,
                                MicroBioDataRepository microBioDataRepository,
                                MolBioDataRepository molBioDataRepository) {
        this.chemDataRepository = chemDataRepository;
        this.microBioDataRepository = microBioDataRepository;
        this.molBioDataRepository = molBioDataRepository;
    }

    // Get Chemical Test Summaries
    public List<ReportSummaryDTO> getChemicalTestSummaries() {
        Map<String, ReportSummaryDTO> summaryMap = new HashMap<>();
    
        // Retrieve all chemical tests from the database
        List<ChemData> chemicalTests = chemDataRepository.findAll();
    
        for (ChemData chemData : chemicalTests) {
            // Split analyte field into individual analytes (if multiple analytes are present)
            String[] analytes = chemData.getAnalyte().split(",\\s*");
    
            // Split remarks into individual results (if multiple remarks are present)
            String[] remarkParts = chemData.getRemarks().split(",\\s*");
    
            // Process each analyte with its corresponding remark
            for (int i = 0; i < analytes.length; i++) {
                String analyte = analytes[i].trim();  // Trim spaces
                String remark = remarkParts[i].trim();  // Trim spaces
    
                // Retrieve or create a ReportSummaryDTO for this analyte
                ReportSummaryDTO summary = summaryMap.get(analyte);
                if (summary == null) {
                    summary = new ReportSummaryDTO(analyte, 0, 0, 0); // Initialize with default counts
                }
    
                // Increment the total test count for this analyte
                summary.setTotalTests(summary.getTotalTests() + 1);
    
                // Process the remark and increment counts
                if ("Positive".equalsIgnoreCase(remark)) {
                    summary.setPositiveResults(summary.getPositiveResults() + 1);
                } else if ("Negative".equalsIgnoreCase(remark)) {
                    summary.setNegativeResults(summary.getNegativeResults() + 1);
                }
    
                // Put the updated summary back in the map
                summaryMap.put(analyte, summary);
            }
        }
    
        // Convert the map values to a list for the result
        return new ArrayList<>(summaryMap.values());
    }
    
    // Get Microbiological Test Summaries
    public List<ReportSummaryDTO> getMicrobiologicalTests() {
        Map<String, ReportSummaryDTO> summaryMap = new HashMap<>();
    
        // Process each microBioData record
        microBioDataRepository.findAll().forEach(microData -> {
            // Split the test names (micTestName) into individual tests
            String[] tests = microData.getMicTestName().split(",\\s*");
            
            // Split the remarks (micRemarks) into individual parts (e.g., Positive, Negative)
            String[] remarks = microData.getMicRemarks().split(",\\s*");
    
            // Ensure both arrays (tests and remarks) are the same length
            int length = Math.min(tests.length, remarks.length);
    
            // Process each test
            for (int i = 0; i < length; i++) {
                String test = tests[i];
                String remark = remarks[i];
    
                // Retrieve or create a ReportSummaryDTO for this test
                ReportSummaryDTO summary = summaryMap.getOrDefault(test,
                        new ReportSummaryDTO(test, 0, 0, 0));
    
                // Update the total number of tests performed
                summary.setTotalTests(summary.getTotalTests() + 1);
    
                // Update positive and negative results based on remarks
                if ("Positive".equalsIgnoreCase(remark)) {
                    summary.setPositiveResults(summary.getPositiveResults() + 1);
                } else if ("Negative".equalsIgnoreCase(remark)) {
                    summary.setNegativeResults(summary.getNegativeResults() + 1);
                }
    
                // Put the updated summary back in the map
                summaryMap.put(test, summary);
            }
        });
    
        // Return the list of ReportSummaryDTO objects
        return new ArrayList<>(summaryMap.values());
    }
      

    // Get Molecular Biology Test Summaries
    public List<ReportSummaryDTO> getMolecularBiologyTests() {
        Map<String, ReportSummaryDTO> summaryMap = new HashMap<>();
    
        // Process each molBioData record
        molBioDataRepository.findAll().forEach(molData -> {
            // Split the test names (testName) into individual tests
            String[] tests = molData.getTestName().split(",\\s*");
            
            // Split the remarks (remarks) into individual parts (e.g., Positive, Negative)
            String[] remarks = molData.getRemarks().split(",\\s*");
    
            // Ensure both arrays (tests and remarks) are the same length
            int length = Math.min(tests.length, remarks.length);
    
            // Process each test
            for (int i = 0; i < length; i++) {
                String test = tests[i];
                String remark = remarks[i];
    
                // Retrieve or create a ReportSummaryDTO for this test
                ReportSummaryDTO summary = summaryMap.getOrDefault(test,
                        new ReportSummaryDTO(test, 0, 0, 0));
    
                // Update the total number of tests performed
                summary.setTotalTests(summary.getTotalTests() + 1);
    
                // Update positive and negative results based on remarks
                if ("Positive".equalsIgnoreCase(remark)) {
                    summary.setPositiveResults(summary.getPositiveResults() + 1);
                } else if ("Negative".equalsIgnoreCase(remark)) {
                    summary.setNegativeResults(summary.getNegativeResults() + 1);
                }
    
                // Put the updated summary back in the map
                summaryMap.put(test, summary);
            }
        });
    
        // Return the list of ReportSummaryDTO objects
        return new ArrayList<>(summaryMap.values());
    }    
    
}
