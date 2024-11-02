package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MolBioData;
import com.example.semiautomatedlims.Service.TestingMolBioService;
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

        for (String key : allParams.keySet()) {
            if (key.startsWith("species_result_")) {
                String testName = key.replace("species_result_", "");
                String speciesResult = allParams.get(key);

                if (testNames.length() > 0) {
                    testNames.append(", ");
                    meatSpeciesResults.append(", ");
                }
                testNames.append(testName);
                meatSpeciesResults.append(speciesResult);
            }
        }

        MolBioData molBioData = new MolBioData();
        molBioData.setLdControlNumber(clientReqForm.getLdControlNumber());
        molBioData.setTestName(testNames.toString());
        molBioData.setMeatSpeciesResult(meatSpeciesResults.toString());

        testingMolBioService.saveMolBioData(molBioData);

        clientReqForm.setMolbioPending("accepted");
        testingMolBioService.saveRequest(clientReqForm);

        return ResponseEntity.ok("Results submitted successfully.");
    }
}
