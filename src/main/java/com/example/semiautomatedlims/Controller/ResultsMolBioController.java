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
            List<String> examinations = request.getExaminations();
            model.addAttribute("examinations", examinations);
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("examinations", List.of());
        }

        return "RESULTS-MolBio";
    }

    @PostMapping("/submitResults")
    public ResponseEntity<String> submitResults(@RequestParam("ldControlNumber") String ldControlNumber,
                                                @RequestParam Map<String, String> allParams) {
        ClientReqForm clientReqForm = testingMolBioService.getRequestDetailsById(Long.valueOf(ldControlNumber));

        if (clientReqForm == null) {
            return ResponseEntity.badRequest().body("Error: LD Control Number not found.");
        }

        for (String key : allParams.keySet()) {
            if (key.startsWith("species_result_")) {
                String examinationName = key.replace("species_result_", "");
                String speciesResult = allParams.get(key);

                MolBioData molBioData = new MolBioData();
                molBioData.setClientReqForm(clientReqForm);
                molBioData.setTestName(examinationName);
                molBioData.setMeatSpeciesResult(speciesResult);

                testingMolBioService.saveMolBioData(molBioData);
            }
        }

        return ResponseEntity.ok("Results submitted successfully.");
    }
}
