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

    public List<ReportSummaryDTO> initializePredefinedTests(
            List<String> predefinedTests,
            Map<Integer, Map<String, ReportSummaryDTO>> yearlyTests) {

        // Initialize a list to hold all test summaries
        List<ReportSummaryDTO> testSummaries = new ArrayList<>();

        // Ensure all months (1-12) are initialized
        for (int month = 1; month <= 12; month++) {
            yearlyTests.putIfAbsent(month, new HashMap<>());
            Map<String, ReportSummaryDTO> monthlyTests = yearlyTests.get(month);

            // Ensure each predefined test exists for this month
            for (String testName : predefinedTests) {
                monthlyTests.putIfAbsent(testName, new ReportSummaryDTO(testName, 0, 0, 0));
            }
        }

        // Populate the list of test summaries with the predefined test names
        for (String testName : predefinedTests) {
            // Aggregate totals across all months for this test
            int totalTests = 0;
            int positiveResults = 0;
            int negativeResults = 0;

            for (int month = 1; month <= 12; month++) {
                ReportSummaryDTO monthlySummary = yearlyTests.get(month).get(testName);
                totalTests += monthlySummary.getTotalTests();
                positiveResults += monthlySummary.getPositiveResults();
                negativeResults += monthlySummary.getNegativeResults();
            }

            testSummaries.add(new ReportSummaryDTO(testName, totalTests, positiveResults, negativeResults));
        }

        return testSummaries;
    }

    // Method to get yearly test summaries
    public Map<Integer, Map<String, ReportSummaryDTO>> getYearlyTestSummaries(int year) {
        Map<Integer, Map<String, ReportSummaryDTO>> yearSummaries = new HashMap<>();
        
        for (int month = 1; month <= 12; month++) {
            List<MicroBioData> monthlyData = getMicrobiologicalTestsForMonth(month, year);
            
            Map<String, ReportSummaryDTO> monthSummary = new HashMap<>();
            
            for (MicroBioData data : monthlyData) {
                // Split the test names and remarks
                String[] testNames = data.getMicTestName().split(",\\s*");
                String[] remarks = data.getMicRemarks().split(",\\s*");

                // Ensure both arrays (testNames and remarks) are the same length
                int length = Math.min(testNames.length, remarks.length);

                for (int i = 0; i < length; i++) {
                    String testName = testNames[i].trim();  // Trim any spaces
                    String remark = remarks[i].trim();  // Trim any spaces
                    
                    // Get or create a summary for the test
                    ReportSummaryDTO summary = monthSummary.getOrDefault(testName, new ReportSummaryDTO(testName, 0, 0, 0));
                    
                    // Increment the total test count for this test
                    summary.setTotalTests(summary.getTotalTests() + 1);
                    
                    // Increment positive or negative results based on the remark
                    if ("Positive".equalsIgnoreCase(remark)) {
                        summary.incrementPositiveResultsForMonth(month);  // Increment for the month
                    } else if ("Negative".equalsIgnoreCase(remark)) {
                        summary.incrementNegativeResultsForMonth(month);  // Increment for the month
                    }
                    
                    // Put the updated summary back in the map
                    monthSummary.put(testName, summary);
                }
            }
            
            yearSummaries.put(month, monthSummary);
        }
        
        return yearSummaries;
    }


    // Method to get yearly test summaries for Molecular Biology Tests
    public Map<Integer, Map<String, ReportSummaryDTO>> getYearlyMolbioTestSummaries(int year) {
        Map<Integer, Map<String, ReportSummaryDTO>> yearSummaries = new HashMap<>();
        
        for (int month = 1; month <= 12; month++) {
            List<MolBioData> monthlyData = getMolecularBiologyTestsForMonth(month, year);
            
            Map<String, ReportSummaryDTO> monthSummary = new HashMap<>();
            
            for (MolBioData data : monthlyData) {
                // Split the test names and remarks
                String[] testNames = data.getTestName().split(",\\s*");
                String[] remarks = data.getRemarks().split(",\\s*");

                // Ensure both arrays (testNames and remarks) are the same length
                int length = Math.min(testNames.length, remarks.length);

                for (int i = 0; i < length; i++) {
                    String testName = testNames[i].trim();  // Trim any spaces
                    String remark = remarks[i].trim();  // Trim any spaces
                    
                    // Get or create a summary for the test
                    ReportSummaryDTO summary = monthSummary.getOrDefault(testName, new ReportSummaryDTO(testName, 0, 0, 0));
                    
                    // Increment the total test count for this test
                    summary.setTotalTests(summary.getTotalTests() + 1);
                    
                    // Increment positive or negative results based on the remark
                    if ("Positive".equalsIgnoreCase(remark)) {
                        summary.incrementPositiveResultsForMonth(month);  // Increment for the month
                    } else if ("Negative".equalsIgnoreCase(remark)) {
                        summary.incrementNegativeResultsForMonth(month);  // Increment for the month
                    }
                    
                    // Put the updated summary back in the map
                    monthSummary.put(testName, summary);
                }
            }
            
            yearSummaries.put(month, monthSummary);
        }
        
        return yearSummaries;
    }

    // Method to get yearly test summaries for Chemical Tests
    public Map<Integer, Map<String, ReportSummaryDTO>> getYearlyChemicalTestSummaries(int year) {
        Map<Integer, Map<String, ReportSummaryDTO>> yearSummaries = new HashMap<>();
        
        for (int month = 1; month <= 12; month++) {
            List<ChemData> monthlyData = getChemicalTestsForMonth(month, year);
            
            Map<String, ReportSummaryDTO> monthSummary = new HashMap<>();
            
            for (ChemData data : monthlyData) {
                // Split the analytes and remarks
                String[] analytes = data.getAnalyte().split(",\\s*");
                String[] remarks = data.getRemarks().split(",\\s*");

                // Ensure both arrays (analytes and remarks) are the same length
                int length = Math.min(analytes.length, remarks.length);

                for (int i = 0; i < length; i++) {
                    String analyte = analytes[i].trim();  // Trim any spaces
                    String remark = remarks[i].trim();  // Trim any spaces
                    
                    // Get or create a summary for the analyte
                    ReportSummaryDTO summary = monthSummary.getOrDefault(analyte, new ReportSummaryDTO(analyte, 0, 0, 0));
                    
                    // Increment the total test count for this analyte
                    summary.setTotalTests(summary.getTotalTests() + 1);
                    
                    // Increment positive or negative results based on the remark
                    if ("Positive".equalsIgnoreCase(remark)) {
                        summary.incrementPositiveResultsForMonth(month);  // Increment for the month
                    } else if ("Negative".equalsIgnoreCase(remark)) {
                        summary.incrementNegativeResultsForMonth(month);  // Increment for the month
                    }
                    
                    // Put the updated summary back in the map
                    monthSummary.put(analyte, summary);
                }
            }
            
            yearSummaries.put(month, monthSummary);
        }
        
        return yearSummaries;
    }


    // Method to fetch microbiological tests for a given month and year
    private List<MicroBioData> getMicrobiologicalTestsForMonth(int month, int year) {
        // Query your database to fetch the data for the given month and year
        // Example query: SELECT * FROM microbio_data WHERE YEAR(analysis_date) = :year AND MONTH(analysis_date) = :month

        // This is a placeholder method that you should replace with the actual database query logic
        return microBioDataRepository.findByMonthAndYear(month, year);
    }

    // Helper method to fetch molecular biology tests for a given month and year
    private List<MolBioData> getMolecularBiologyTestsForMonth(int month, int year) {
        // Query your database to fetch the data for the given month and year
        return molBioDataRepository.findByMonthAndYear(month, year);
    }

    // Helper method to fetch chemical tests for a given month and year
    private List<ChemData> getChemicalTestsForMonth(int month, int year) {
        // Query your database to fetch the data for the given month and year
        return chemDataRepository.findByMonthAndYear(month, year);
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
