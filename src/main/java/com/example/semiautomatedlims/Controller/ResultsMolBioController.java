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
            model.addAttribute("clientReqid", clientReqid); // Pass clientReqid to the model
            List<String> molecexaminations = request.getMolecExaminations();
            model.addAttribute("examinations", molecexaminations);
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("clientReqid", clientReqid); // Ensure clientReqid is still passed even if the request is not found
            model.addAttribute("examinations", List.of());
        }

        return "RESULTS-MolBio";
    }

    @PostMapping("/submitResults")
    public ResponseEntity<String> submitResults(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam Map<String, String> allParams) {
        // Retrieve client request form by clientReqid
        ClientReqForm clientReqForm = testingMolBioService.getRequestDetailsById(clientReqid);

        if (clientReqForm == null) {
            return ResponseEntity.badRequest().body("Error: Client request ID not found.");
        }

        // Variables to hold concatenated results
        StringBuilder testNames = new StringBuilder();
        StringBuilder meatSpeciesResults = new StringBuilder();

        // Loop through the results and build comma-separated strings
        for (String key : allParams.keySet()) {
            if (key.startsWith("species_result_")) {
                String testName = key.replace("species_result_", "");
                String speciesResult = allParams.get(key);

                // Concatenate the test names and species results with commas
                if (testNames.length() > 0) {
                    testNames.append(", ");
                    meatSpeciesResults.append(", ");
                }
                testNames.append(testName);
                meatSpeciesResults.append(speciesResult);
            }
        }

        // Create a new MolBioData entity
        MolBioData molBioData = new MolBioData();
        molBioData.setLdControlNumber(clientReqForm.getLdControlNumber());  // Using ldControlNumber directly
        molBioData.setTestName(testNames.toString());  // Concatenated test names
        molBioData.setMeatSpeciesResult(meatSpeciesResults.toString());  // Concatenated species results

        // Save the data into the MolBioData table
        testingMolBioService.saveMolBioData(molBioData);

        // Update molbio_pending to 'accepted'
        clientReqForm.setMolbioPending("accepted");
        testingMolBioService.saveRequest(clientReqForm);  // Save the updated ClientReqForm entity

        return ResponseEntity.ok("Results submitted successfully.");
    }
}
