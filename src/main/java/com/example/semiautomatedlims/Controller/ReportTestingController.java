package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.ReportSummaryDTO;
import com.example.semiautomatedlims.Service.ReportTestingService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/REPORT-testing")
public class ReportTestingController {

    private final ReportTestingService reportTestingService;

    @Autowired
    public ReportTestingController(ReportTestingService reportTestingService) {
        this.reportTestingService = reportTestingService;
    }

    @GetMapping
    public String getChemicalTestSummary(Model model) {

        // Predefined Test Names
        List<String> predefinedMicrobioTests = Arrays.asList(
            "standard_count", "staphylococcus", "salmonella", "campylobacter", 
            "CST_gram_positive_ast", "CST_gram_negative_ast", "coliform", 
            "e_coli", "e_coli2", "yeast", "organoleptic", "pH", "trichinella"
        );
        List<String> predefinedMolbioTests = Arrays.asList(
            "dog", "cat", "chicken", "buffalo", "cattle", 
            "horse", "goat", "sheep", "swine"
        );
        List<String> predefinedChemicalTests = Arrays.asList(
            "beta-lactams", "tetracyclines", "sulfonamides", "aminoglycosides", 
            "macrolides", "quinolones", "chloramphenicol", "nitrofuran-aoz", 
            "nitrofuran-amoz", "corticosteroids", "olaquindox", "beta-agonists", 
            "stilbenes", "ractopamine"
        );

        // Fetch existing yearly test summaries
        int year = 2024;
        Map<Integer, Map<String, ReportSummaryDTO>> yearlyMicrobioTests = reportTestingService.getYearlyTestSummaries(year);
        Map<Integer, Map<String, ReportSummaryDTO>> yearlyMolbioTests = reportTestingService.getYearlyMolbioTestSummaries(year);
        Map<Integer, Map<String, ReportSummaryDTO>> yearlyChemicalTests = reportTestingService.getYearlyChemicalTestSummaries(year);

        // Initialize tests for each category using the service and ensure missing months are filled
        List<ReportSummaryDTO> microbiologicalTests = reportTestingService.initializePredefinedTests(predefinedMicrobioTests, yearlyMicrobioTests);
        List<ReportSummaryDTO> molecularBiologyTests = reportTestingService.initializePredefinedTests(predefinedMolbioTests, yearlyMolbioTests);
        List<ReportSummaryDTO> chemicalSummaries = reportTestingService.initializePredefinedTests(predefinedChemicalTests, yearlyChemicalTests);
        
        // Ensure all months (1-12) are initialized and contain the test data
        for (int month = 1; month <= 12; month++) {
            // Initialize the map for each month if it doesn't exist
            yearlyMicrobioTests.putIfAbsent(month, new HashMap<>());
            yearlyMolbioTests.putIfAbsent(month, new HashMap<>());
            yearlyChemicalTests.putIfAbsent(month, new HashMap<>());
            
            // Ensure each test for the month has initialized values for total, positive, and negative results
            for (ReportSummaryDTO test : microbiologicalTests) {
                String testName = test.getTestName();
                Map<String, ReportSummaryDTO> monthlyData = yearlyMicrobioTests.get(month);
                
                // If the test doesn't exist for the month, initialize it with zeroes
                monthlyData.putIfAbsent(testName, new ReportSummaryDTO(testName, 0, 0, 0));
            }

            for (ReportSummaryDTO test : molecularBiologyTests) {
                String testName = test.getTestName();
                Map<String, ReportSummaryDTO> monthlyData = yearlyMolbioTests.get(month);
                monthlyData.putIfAbsent(testName, new ReportSummaryDTO(testName, 0, 0, 0));
            }

            for (ReportSummaryDTO test : chemicalSummaries) {
                String testName = test.getTestName();
                Map<String, ReportSummaryDTO> monthlyData = yearlyChemicalTests.get(month);
                monthlyData.putIfAbsent(testName, new ReportSummaryDTO(testName, 0, 0, 0));
            }
        }

        model.addAttribute("chemicalSummaries", chemicalSummaries);
        model.addAttribute("microbiologicalTests", microbiologicalTests);
        model.addAttribute("molecularBiologyTests", molecularBiologyTests);
        model.addAttribute("yearlyMicrobioTests", yearlyMicrobioTests);  // Data for all months
        model.addAttribute("yearlyMolbioTests", yearlyMolbioTests);
        model.addAttribute("yearlyChemicalTests", yearlyChemicalTests);
    
        return "REPORT-testing";  // Updated to match the HTML filename
    }
}

