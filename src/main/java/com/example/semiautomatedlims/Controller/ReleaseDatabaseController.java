package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Service.ReportReleaseService;
import com.example.semiautomatedlims.Service.TestingMicrobioService;  // Corrected service name
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.ArrayList;

@Controller
public class ReleaseDatabaseController {

    private final ReportReleaseService reportReleaseService;
    private final TestingMicrobioService testingMicrobioService;  // Corrected service name

    // Hard-coded list of tests you want to check
    private final List<String> hardCodedTests = List.of(
        "Standard Plate Count",
        "Staphylococcus aureus",
        "Salmonella sp.",
        "Campylobacter",
        "Culture and Sensitivity Test: Gram Positive AST",
        "Culture and Sensitivity Test: Gram Negative AST",
        "Coliform Count",
        "E. Coli",
        "E. Coli & E. Coli O157;H7",
        "Yeast and Molds",
        "Organoleptic Test",
        "pH",
        "Trichinella spp. Identification"
    );

    @Autowired
    public ReleaseDatabaseController(ReportReleaseService reportReleaseService, TestingMicrobioService testingMicrobioService) {
        this.reportReleaseService = reportReleaseService;
        this.testingMicrobioService = testingMicrobioService;  // Injecting the correct service
    }

    @GetMapping("/RELEASE-database")
    public String showReleaseDatabasePage(Model model) {
        // Fetch completed requests and MicroBioData entries
        List<MicroBioData> microBioDataList = testingMicrobioService.getAllMicroBioData();  // Updated service method

        // Process the MicroBioData entries
        List<ProcessedTestData> processedData = processTestData(microBioDataList);

        // Add both completed requests and processed data to the model
        model.addAttribute("completedRequests", reportReleaseService.getCompletedRequests());
        model.addAttribute("processedData", processedData);

        return "RELEASE-database";  // Return the view name
    }

    private List<ProcessedTestData> processTestData(List<MicroBioData> microBioDataList) {
        return microBioDataList.stream().map(microBioData -> {
            // Process the test_name CSV into individual tests
            String[] testNames = microBioData.getMicTestName().split(",");  // Updated field

            // Create an object to hold the processed results for each MicroBioData entry
            ProcessedTestData processedData = new ProcessedTestData();
            processedData.setControlNumber(microBioData.getLdControlNumber());

            for (String test : hardCodedTests) {
                boolean found = false;
                for (String testName : testNames) {
                    if (testName.trim().equalsIgnoreCase(test)) {
                        processedData.addTestResult(test, microBioData.getMicResult());  // Updated field
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    processedData.addTestResult(test, "N/A");
                }
            }
            return processedData;
        }).toList();
    }

    public static class ProcessedTestData {
        private String controlNumber;
        private final List<TestResult> testResults = new ArrayList<>();

        public void setControlNumber(String controlNumber) {
            this.controlNumber = controlNumber;
        }

        public String getControlNumber() {
            return controlNumber;
        }

        public void addTestResult(String testName, String result) {
            testResults.add(new TestResult(testName, result));
        }

        public List<TestResult> getTestResults() {
            return testResults;
        }

        public static class TestResult {
            private String testName;
            private String result;

            public TestResult(String testName, String result) {
                this.testName = testName;
                this.result = result;
            }

            public String getTestName() {
                return testName;
            }

            public String getResult() {
                return result;
            }
        }
    }
}
