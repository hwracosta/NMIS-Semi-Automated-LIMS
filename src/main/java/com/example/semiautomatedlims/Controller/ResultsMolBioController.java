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
import com.example.semiautomatedlims.Entity.MolBioData;
import com.example.semiautomatedlims.Service.TestingMolBioService;

@Controller
public class ResultsMolBioController {

    @Autowired
    private TestingMolBioService testingMolBioService;

    @GetMapping("/RESULTS-MolBio")
    public String showResultMolbioPage(@RequestParam("clientReqid") Long clientReqid, Model model) {
        ClientReqForm request = testingMolBioService.getRequestDetailsById(clientReqid);

        if (request != null) {
            model.addAttribute("ldControlNumber", request.getLdControlNumber());
            model.addAttribute("clientReqid", clientReqid);
            List<String> molecexaminations = request.getMolecExaminations();
            model.addAttribute("examinations", molecexaminations);
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("clientReqid", clientReqid);
            model.addAttribute("examinations", List.of());
        }

        return "RESULTS-MolBio";
    }

    @PostMapping("/submitResults")
    public ResponseEntity<String> submitResults(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam Map<String, String> allParams) {
        ClientReqForm clientReqForm = testingMolBioService.getRequestDetailsById(clientReqid);

        if (clientReqForm == null) {
            return ResponseEntity.badRequest().body("Error: Client request ID not found.");
        }

        StringBuilder testNames = new StringBuilder();
        StringBuilder meatSpeciesResults = new StringBuilder();
        StringBuilder remarks = new StringBuilder(); // Initialize StringBuilder for remarks

        for (String key : allParams.keySet()) {
            if (key.startsWith("species_result_")) {
                String testName = key.replace("species_result_", "");
                String speciesResult = allParams.get(key);
                String remark = allParams.get("remarks_" + testName); // Retrieve the corresponding remark

                if (testNames.length() > 0) {
                    testNames.append(", ");
                    meatSpeciesResults.append(", ");
                    remarks.append(", ");
                }
                testNames.append(testName);
                meatSpeciesResults.append(speciesResult);
                remarks.append(remark != null ? remark : ""); // Append remark or an empty string if null
            }
        }

        MolBioData molBioData = new MolBioData();
        molBioData.setLdControlNumber(clientReqForm.getLdControlNumber());
        molBioData.setTestName(testNames.toString());
        molBioData.setMeatSpeciesResult(meatSpeciesResults.toString());
        molBioData.setRemarks(remarks.toString()); // Set remarks in MolBioData

        testingMolBioService.saveMolBioData(molBioData);

        clientReqForm.setMolbioPending("accepted");
        testingMolBioService.saveRequest(clientReqForm);

        return ResponseEntity.ok("Results submitted successfully.");
    }
}
