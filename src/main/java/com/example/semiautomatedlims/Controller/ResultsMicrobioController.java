package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Service.TestingMicrobioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ResultsMicrobioController {

    @Autowired
    private TestingMicrobioService testingMicrobioService;

    @GetMapping("/RESULTS-Microbio")
    public String showResultMicroBioPage(@RequestParam("clientReqid") Long clientReqid, Model model) {
        ClientReqForm request = testingMicrobioService.getRequestDetailsById(clientReqid);

        if (request != null) {
            model.addAttribute("ldControlNumber", request.getLdControlNumber());
            model.addAttribute("clientReqid", clientReqid); // Pass clientReqid to the model
            List<String> examinations = request.getMicrobioExaminations();
            model.addAttribute("examinations", examinations);
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("clientReqid", clientReqid); // Ensure clientReqid is still passed even if the request is not found
            model.addAttribute("examinations", List.of());
        }

        return "RESULTS-Microbio";
    }

    @PostMapping("/submitMicrobioResults")
    public ResponseEntity<String> submitResults(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam Map<String, String> allParams) {
        // Retrieve client request form by clientReqid
        ClientReqForm clientReqForm = testingMicrobioService.getRequestDetailsById(clientReqid);

        if (clientReqForm == null) {
            return ResponseEntity.badRequest().body("Error: Client request ID not found.");
        }

        // Variables to hold concatenated values
        StringBuilder micTestNames = new StringBuilder();
        StringBuilder micResults = new StringBuilder();
        StringBuilder micRefVals = new StringBuilder();
        StringBuilder micRemarks = new StringBuilder();

        // Loop through the results and build comma-separated strings for each field
        for (String key : allParams.keySet()) {
            if (key.startsWith("species_result_")) {
                String testName = key.replace("species_result_", "");
                String result = allParams.get(key);
                String refValue = allParams.get("ref_value_" + testName);
                String remarks = allParams.get("remarks_" + testName);

                // Concatenate the test names and corresponding fields
                if (micTestNames.length() > 0) {
                    micTestNames.append(", ");
                    micResults.append(", ");
                    micRefVals.append(", ");
                    micRemarks.append(", ");
                }
                micTestNames.append(testName);
                micResults.append(result);
                micRefVals.append(refValue);
                micRemarks.append(remarks);
            }
        }

        // Create a new MicroBioData entity
        MicroBioData microBioData = new MicroBioData();
        microBioData.setLdControlNumber(clientReqForm.getLdControlNumber());  // Using ldControlNumber directly
        microBioData.setMicTestName(micTestNames.toString());  // Concatenated test names
        microBioData.setMicResult(micResults.toString());  // Concatenated results
        microBioData.setMicRefVal(micRefVals.toString());  // Concatenated reference values
        microBioData.setMicRemarks(micRemarks.toString());  // Concatenated remarks

        // Save the data into the MicroBioData table
        testingMicrobioService.saveMicroBioData(microBioData);

        return ResponseEntity.ok("Results submitted successfully.");
    }
}
