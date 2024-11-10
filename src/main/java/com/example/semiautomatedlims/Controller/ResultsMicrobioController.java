package com.example.semiautomatedlims.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Service.TestingMicrobioService;

@Controller
public class ResultsMicrobioController {

    @Autowired
    private TestingMicrobioService testingMicrobioService;

    @GetMapping("/RESULTS-Microbio")
    public String showResultMicroBioPage(@RequestParam("clientReqid") Long clientReqid, Model model) {
        ClientReqForm request = testingMicrobioService.getRequestDetailsById(clientReqid);

        if (request != null) {
            model.addAttribute("ldControlNumber", request.getLdControlNumber());
            model.addAttribute("clientReqid", clientReqid);
            List<String> examinations = request.getMicrobioExaminations();
            model.addAttribute("examinations", examinations);
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("clientReqid", clientReqid);
            model.addAttribute("examinations", List.of());
        }

        return "RESULTS-Microbio";
    }

    @PostMapping("/submitMicrobioResults")
    public ResponseEntity<String> submitResults(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam Map<String, String> allParams) {
        ClientReqForm clientReqForm = testingMicrobioService.getRequestDetailsById(clientReqid);

        if (clientReqForm == null) {
            return ResponseEntity.badRequest().body("Error: Client request ID not found.");
        }

        StringBuilder micTestNames = new StringBuilder();
        StringBuilder micResults = new StringBuilder();
        StringBuilder micRefVals = new StringBuilder();
        StringBuilder micRemarks = new StringBuilder();

        for (String key : allParams.keySet()) {
            if (key.startsWith("micResult_")) {
                String testName = key.replace("micResult_", "");
                String result = allParams.get(key);
                String refValue = allParams.get("micRefVal_" + testName);
                String remarks = allParams.get("micRemarks_" + testName);

                if (micTestNames.length() > 0) {
                    micTestNames.append(", ");
                    micResults.append(", ");
                    micRefVals.append(", ");
                    micRemarks.append(", ");
                }
                micTestNames.append(testName);
                micResults.append(result);
                micRefVals.append(refValue == null || refValue.isEmpty() ? "N/A" : refValue);
                micRemarks.append(remarks == null || remarks.isEmpty() ? "N/A" : remarks);
            }
        }

        MicroBioData microBioData = new MicroBioData();
        microBioData.setLdControlNumber(clientReqForm.getLdControlNumber());
        microBioData.setMicTestName(micTestNames.toString());
        microBioData.setMicResult(micResults.toString());
        microBioData.setMicRefVal(micRefVals.toString());
        microBioData.setMicRemarks(micRemarks.toString());

        testingMicrobioService.saveMicroBioData(microBioData);

        clientReqForm.setMicrobioPending("accepted");
        testingMicrobioService.saveRequest(clientReqForm);

        return ResponseEntity.ok("MicroBio Results submitted successfully.");
    }
}
