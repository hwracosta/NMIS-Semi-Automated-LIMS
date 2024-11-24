package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Repository.ChemDataRepository;
import com.example.semiautomatedlims.Repository.MicroBioDataRepository;
import com.example.semiautomatedlims.Service.ReportReleaseService;
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
    private final ChemDataRepository chemDataRepository;

    // Hardcoded Microbiological Tests
    private final List<String> microbioTests = List.of(
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

    // Hardcoded Chemical Tests
    private final List<String> chemTests = List.of(
            "Beta-lactams",
            "Tetracyclines",
            "Sulfonamides",
            "Aminoglycosides",
            "Macrolides",
            "Quinolones",
            "Chloramphenicol",
            "Nitrofuran AOZ",
            "Nitrofuran AMOZ",
            "Corticosteroids",
            "Olaquindox",
            "Beta-agonists",
            "Stilbenes",
            "Ractopamine"
    );

    @Autowired
    public ReleaseDatabaseController(ReportReleaseService reportReleaseService,
                                     MicroBioDataRepository microBioDataRepository,
                                     ChemDataRepository chemDataRepository) {
        this.reportReleaseService = reportReleaseService;
        this.microBioDataRepository = microBioDataRepository;
        this.chemDataRepository = chemDataRepository;
    }

    @GetMapping("/RELEASE-database")
    public String showReleaseDatabasePage(Model model) {
        var completedRequests = reportReleaseService.getCompletedRequests();

        for (var request : completedRequests) {
            String controlNumber = request.getLdControlNumber();

            // Process Microbiological Test Results
            List<MicroBioData> microbioTestData = microBioDataRepository.findByLdControlNumber(controlNumber);
            Map<String, String> microbioResults = processTestData(microbioTestData, microbioTests, "microbio");

            // Process Chemical Test Results
            List<ChemData> chemTestData = chemDataRepository.findByLdControlNumber(controlNumber);
            Map<String, String> chemResults = processChemData(chemTestData, chemTests);

            // Combine both results into the request object
            Map<String, String> combinedResults = new HashMap<>(microbioResults);
            combinedResults.putAll(chemResults);

            // Set the combined results map to the request
            request.setTestResultsMap(combinedResults);
        }

        model.addAttribute("completedRequests", completedRequests);
        return "RELEASE-database";
    }

    private Map<String, String> processTestData(List<?> testData, List<String> hardcodedTests, String type) {
        Map<String, String> results = hardcodedTests.stream()
                .collect(Collectors.toMap(test -> test, test -> "N/A")); // Initialize with "N/A"

        for (Object data : testData) {
            String[] testNames, testResults;

            if (type.equals("microbio") && data instanceof MicroBioData) {
                testNames = ((MicroBioData) data).getMicTestName().split("\\s*,\\s*");
                testResults = ((MicroBioData) data).getMicResult().split("\\s*,\\s*");
            } else {
                continue;
            }

            for (int i = 0; i < testNames.length; i++) {
                String normalizedTestName = normalizeTestName(testNames[i].trim(), type);
                String result = i < testResults.length ? testResults[i].trim() : "N/A";
                if (results.containsKey(normalizedTestName)) {
                    results.put(normalizedTestName, result);
                }
            }
        }

        return results;
    }

    private Map<String, String> processChemData(List<ChemData> chemTestData, List<String> chemTests) {
        Map<String, String> results = chemTests.stream()
                .collect(Collectors.toMap(test -> test, test -> "N/A")); // Initialize with "N/A"

        for (ChemData data : chemTestData) {
            String[] analytes = data.getAnalyte().split("\\s*,\\s*");
            String[] resultsArray = data.getResult().split("\\s*,\\s*");

            for (int i = 0; i < analytes.length; i++) {
                String normalizedTestName = normalizeTestName(analytes[i].trim(), "chem");
                String result = i < resultsArray.length ? resultsArray[i].trim() : "N/A";
                if (results.containsKey(normalizedTestName)) {
                    results.put(normalizedTestName, result);
                }
            }
        }

        return results;
    }

    private String normalizeTestName(String testName, String type) {
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

            case "beta-lactams" -> "Beta-lactams";
            case "tetracyclines" -> "Tetracyclines";
            case "sulfonamides" -> "Sulfonamides";
            case "aminoglycosides" -> "Aminoglycosides";
            case "macrolides" -> "Macrolides";
            case "quinolones" -> "Quinolones";
            case "chloramphenicol" -> "Chloramphenicol";
            case "nitrofuran-aoz" -> "Nitrofuran AOZ";
            case "nitrofuran-amoz" -> "Nitrofuran AMOZ";
            case "corticosteroids" -> "Corticosteroids";
            case "olaquindox" -> "Olaquindox";
            case "beta-agonists" -> "Beta-agonists";
            case "stilbenes" -> "Stilbenes";
            case "ractopamine" -> "Ractopamine";
            default -> testName;
        };
    }
}
