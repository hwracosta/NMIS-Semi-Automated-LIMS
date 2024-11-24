package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Service.ReportReleaseService;
import com.example.semiautomatedlims.Repository.MicroBioDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ReleaseDatabaseController {

    private final ReportReleaseService reportReleaseService;
    private final MicroBioDataRepository microBioDataRepository;

    private final List<String> hardCodedTests = List.of(
            "Standard Plate Count",
            "Staphylococcus aureus",
            "Salmonella sp.",
            "Campylobacter",
            "CST Gram Positive AST",
            "CST Gram Negative AST",
            "Coliform Count",
            "E. Coli",
            "E. Coli & E. Coli O157;H7",
            "Yeast and Molds",
            "Organoleptic Test",
            "pH",
            "Trichinella spp. Identification"
    );

    @Autowired
    public ReleaseDatabaseController(ReportReleaseService reportReleaseService, MicroBioDataRepository microBioDataRepository) {
        this.reportReleaseService = reportReleaseService;
        this.microBioDataRepository = microBioDataRepository;
    }

    @GetMapping("/RELEASE-database")
    public String showReleaseDatabasePage(Model model) {
        // Fetch all completed requests
        var completedRequests = reportReleaseService.getCompletedRequests();

        // For each request, fetch associated MicroBioData and process
        Map<String, Map<String, String>> testResultsMap = new HashMap<>();
        for (var request : completedRequests) {
            String controlNumber = request.getLdControlNumber();
            List<MicroBioData> testData = microBioDataRepository.findByLdControlNumber(controlNumber);

            // Process test data into a mapping of test names to results
            Map<String, String> resultMapping = processTestData(testData);
            testResultsMap.put(controlNumber, resultMapping);

            // Set the mapping to the request for display in the view
            request.setTestResultsMap(resultMapping);
        }

        model.addAttribute("completedRequests", completedRequests);
        return "RELEASE-database";
    }

    private Map<String, String> processTestData(List<MicroBioData> testData) {
        Map<String, String> results = hardCodedTests.stream()
                .collect(Collectors.toMap(test -> test, test -> "N/A")); // Initialize with "N/A" for all tests

        for (MicroBioData data : testData) {
            String[] testNames = data.getMicTestName().split("\\s*,\\s*"); // Split test names
            String[] testResults = data.getMicResult().split("\\s*,\\s*"); // Split results

            // Iterate over all test names and map results
            for (int i = 0; i < testNames.length; i++) {
                String normalizedTestName = normalizeTestName(testNames[i].trim()); // Normalize names to match hardcoded list
                String result = i < testResults.length ? testResults[i].trim() : "N/A"; // Get result or default to "N/A"
                if (results.containsKey(normalizedTestName)) {
                    results.put(normalizedTestName, result);
                }
            }
        }
        return results;
    }

    private String normalizeTestName(String testName) {
        return switch (testName.toLowerCase()) {
            case "standard_count" -> "Standard Plate Count";
            case "staphylococcus" -> "Staphylococcus aureus";
            case "salmonella" -> "Salmonella sp.";
            case "campylobacter" -> "Campylobacter";
            case "cst_gram_positive_ast" -> "CST Gram Positive AST";
            case "cst_gram_negative_ast" -> "CST Gram Negative AST";
            case "coliform" -> "Coliform Count";
            case "e_coli" -> "E. Coli";
            case "e_coli2" -> "E. Coli & E. Coli O157;H7";
            case "yeast" -> "Yeast and Molds";
            case "organoleptic" -> "Organoleptic Test";
            case "ph" -> "pH";
            case "trichinella" -> "Trichinella spp. Identification";
            default -> testName; // Default to the raw name if no match
        };
    }
}
