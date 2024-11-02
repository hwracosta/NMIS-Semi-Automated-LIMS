package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Service.TestingChemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Controller
public class ResultsChemController {

    @Autowired
    private TestingChemService testingChemService;

    @GetMapping("/RESULTS-Chem")
    public String showResultChemPage(@RequestParam("clientReqid") Long clientReqid, Model model) {
        ClientReqForm request = testingChemService.getRequestDetailsById(clientReqid);

        if (request != null) {
            model.addAttribute("ldControlNumber", request.getLdControlNumber());
            model.addAttribute("clientReqid", clientReqid);
            List<String> examinations = request.getChemExaminations();
            model.addAttribute("examinations", examinations);
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("clientReqid", clientReqid);
            model.addAttribute("examinations", List.of());
        }

        return "RESULTS-Chem";
    }

    @PostMapping("/submitChemResults")
    public ResponseEntity<String> submitResults(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam Map<String, String> allParams) {
        ClientReqForm clientReqForm = testingChemService.getRequestDetailsById(clientReqid);

        if (clientReqForm == null) {
            return ResponseEntity.badRequest().body("Error: Client request ID not found.");
        }

        StringBuilder chemTestNames = new StringBuilder();
        StringBuilder chemResults = new StringBuilder();
        StringBuilder chemRemarks = new StringBuilder();
        StringBuilder chemDetectionLimits = new StringBuilder();
        StringBuilder chemRegulatoryLimits = new StringBuilder();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date analysisDate = null;

        for (String key : allParams.keySet()) {
            if (key.startsWith("chemResult_")) {
                String testName = key.replace("chemResult_", "");
                String result = allParams.get(key);
                String remarks = allParams.get("chemRemarks_" + testName);
                String detectionLimit = allParams.get("chemDetectionLimit_" + testName);
                String regulatoryLimits = allParams.get("chemRegulatoryLimits_" + testName);
                String analysisDateStr = allParams.get("chemAnalysisDate_" + testName);

                if (chemTestNames.length() > 0) {
                    chemTestNames.append(", ");
                    chemResults.append(", ");
                    chemRemarks.append(", ");
                    chemDetectionLimits.append(", ");
                    chemRegulatoryLimits.append(", ");
                }

                chemTestNames.append(testName);
                chemResults.append(result);
                chemRemarks.append(remarks);
                chemDetectionLimits.append(detectionLimit);
                chemRegulatoryLimits.append(regulatoryLimits);

                try {
                    analysisDate = dateFormat.parse(analysisDateStr);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body("Invalid date format for test: " + testName);
                }
            }
        }

        ChemData chemData = new ChemData();
        chemData.setLdControlNumber(clientReqForm.getLdControlNumber());
        chemData.setClientReqid(clientReqid);
        chemData.setAnalyte(chemTestNames.toString());
        chemData.setResult(chemResults.toString());
        chemData.setRemarks(chemRemarks.toString());
        chemData.setDetectionLimit(chemDetectionLimits.toString());
        chemData.setRegulatoryLimits(chemRegulatoryLimits.toString());
        chemData.setAnalysisDate(analysisDate);

        testingChemService.saveChemData(chemData);

        clientReqForm.setChemPending("accepted");
        testingChemService.saveRequest(clientReqForm);

        return ResponseEntity.ok("Results submitted successfully.");
    }
}
