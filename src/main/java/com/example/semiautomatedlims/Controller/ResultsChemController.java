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

import java.util.Map;

@Controller
public class ResultsChemController {

    @Autowired
    private TestingChemService testingChemService;

    @GetMapping("/RESULTS-Chem")
    public String showResultChemPage(@RequestParam("clientReqid") Long clientReqid, Model model) {
        // Fetch request details using clientReqid
        ClientReqForm request = testingChemService.getRequestDetailsById(clientReqid);

        if (request != null) {
            model.addAttribute("ldControlNumber", request.getLdControlNumber());
            model.addAttribute("clientReqid", clientReqid); // Pass clientReqid to the model
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("clientReqid", clientReqid); // Ensure clientReqid is still passed even if the request is not found
        }

        return "RESULTS-Chem"; // Return the template
    }

    @PostMapping("/submitResults")
    public ResponseEntity<String> submitResults(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam Map<String, String> allParams) {
        // Retrieve client request form by clientReqid
        ClientReqForm clientReqForm = testingChemService.getRequestDetailsById(clientReqid);

        if (clientReqForm == null) {
            return ResponseEntity.badRequest().body("Error: Client request ID not found.");
        }

        // Create a new ChemResult entity and populate it with form data
        ChemData chemData = new ChemData();
        chemData.setLdControlNumber(clientReqForm.getLdControlNumber());
        chemData.setClientReqid(clientReqid);
        chemData.setAnalyte(allParams.get("analyte"));
        chemData.setResult(allParams.get("result"));
        chemData.setRemarks(allParams.get("remarks"));
        chemData.setDetectionLimit(allParams.get("detectionLimit"));
        chemData.setRegulatoryLimits(allParams.get("regulatoryLimits"));
        chemData.setAnalysisDate(java.sql.Date.valueOf(allParams.get("analysisDate")));

        // Save the result into the database
        testingChemService.saveChemData(chemData);

        return ResponseEntity.ok("Results submitted successfully.");
    }
}
