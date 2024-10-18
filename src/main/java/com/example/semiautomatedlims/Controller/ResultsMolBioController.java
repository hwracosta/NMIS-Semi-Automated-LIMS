package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.TestingMolBioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ResultsMolBioController {

    @Autowired
    private TestingMolBioService testingMolBioService;

    @GetMapping("/RESULTS-MolBio")
    public String showResultMolbioPage(@RequestParam("clientReqid") Long clientReqid, Model model) {
        // Fetch the request details by clientReqid
        ClientReqForm request = testingMolBioService.getRequestDetailsById(clientReqid);

        if (request != null) {
            // Add the LD Control Number and Examinations Conducted to the model
            model.addAttribute("ldControlNumber", request.getLdControlNumber());
            
            // Assuming request.getExaminations() returns the list of tests or examinations conducted
            List<String> examinations = request.getExaminations(); // This should be defined in your entity
            model.addAttribute("examinations", examinations);
        } else {
            model.addAttribute("ldControlNumber", "Not available");
            model.addAttribute("examinations", List.of()); // Empty list if not found
        }

        return "RESULTS-MolBio";
    }
}
