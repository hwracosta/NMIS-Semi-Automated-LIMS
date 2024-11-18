package com.example.semiautomatedlims.Controller;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.TestingMolBioService;


@Controller
public class TestingMolBioController {


    @Autowired
    private TestingMolBioService testingMolBioService; // Inject your service here


    @GetMapping("/TESTING-MolBio")
    public String staffTestingHome(Model model) {
        List<ClientReqForm> testingRequests = testingMolBioService.getFilteredRequests(); // Fetch filtered requests
        model.addAttribute("requests", testingRequests); // Add requests to model
        return "TESTING-MolBio";
    }


    @GetMapping("/api/getMolBioRequestDetails")
    @ResponseBody
    public String getTestRequestDetails(@RequestParam Long clientReqid) {
        
        ClientReqForm requestDetails = testingMolBioService.getRequestDetailsById(clientReqid);
        
        if (requestDetails == null) {
            return "<p>No details found for this request.</p>"; 
        }
        
        StringBuilder details = new StringBuilder();
        
        details.append("<div class='checklist'>");
        String[] tests = requestDetails.getMolecTests() != null ? requestDetails.getMolecTests().split(",") : new String[0];
        
        String ldControlNumber = requestDetails.getLdControlNumber();
        for (String test : tests) {
            String sanitizedTest = test.trim().replaceAll(" ", "_"); 
            String capitalizedTest = capitalize(test.trim()); 
    
            details.append("<label><input type='checkbox' id='")
                   .append(ldControlNumber).append("_").append(sanitizedTest).append("' name='tests' value='")
                   .append(test).append("'> ")
                   .append(capitalizedTest).append("</label><br>"); 
        }
        
        details.append("</div>");
        
        return details.toString(); 
    }

    @GetMapping("/api/getSampleDetails")
    @ResponseBody
    public Map<String, Object> getClientDetails(@RequestParam Long clientReqid) {
        ClientReqForm request = testingMolBioService.getRequestDetailsById(clientReqid);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (request != null && request.getClient() != null) {
            Map<String, Object> clientDetails = new HashMap<>();
            clientDetails.put("sampleCategory", capitalize(request.getSampleCategory()));
            clientDetails.put("sampleDetails", capitalize(request.getSampleDetails()));
            clientDetails.put("sampleCode", capitalize(request.getClientSampleCode()));
            clientDetails.put("sampleSource", capitalize(request.getSampleSource()));
            clientDetails.put("productionDate", request.getProductionDate() != null ? dateFormat.format(request.getProductionDate()) : null);
            clientDetails.put("expirationDate", request.getExpirationDate() != null ? dateFormat.format(request.getExpirationDate()) : null);
            clientDetails.put("samplingDate", request.getSamplingDate() != null ? dateFormat.format(request.getSamplingDate()) : null);
            clientDetails.put("weightGrams", request.getWeightGrams());
            clientDetails.put("purposeTest", capitalize(request.getPurposeTest()));
            return clientDetails;
        } else {
            return Map.of("error", "Sample details not found");
        }
    }

    
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] parts = input.split(",");
        StringBuilder capitalizedString = new StringBuilder();

        for (String part : parts) {
            
            part = part.trim().replace("_", " "); 
            if (part.length() > 0) {
                String[] words = part.split(" "); 
                StringBuilder capitalizedPart = new StringBuilder();

                for (int i = 0; i < words.length; i++) {
                    
                    if (words[i].length() > 0) {
                        capitalizedPart.append(Character.toUpperCase(words[i].charAt(0)))
                                    .append(words[i].substring(1).toLowerCase());
                    }
                    if (i < words.length - 1) {
                        capitalizedPart.append(" "); 
                    }
                }

                capitalizedString.append(capitalizedPart).append(", "); 
            }
        }
        
        if (capitalizedString.length() > 0) {
            capitalizedString.setLength(capitalizedString.length() - 2); 
        }

        return capitalizedString.toString();
    }    

    @PostMapping("/api/submitMolBioRequest")
    public ResponseEntity<String> submitMolBioRequest(@RequestParam Long clientReqid) {
        ClientReqForm request = testingMolBioService.getRequestDetailsById(clientReqid);
        if (request != null) {
            request.setIsMolBioTransferred(true); // Add a flag or marker to show it was submitted
            testingMolBioService.saveRequest(request); // Save the updated request


            return ResponseEntity.ok("Request successfully submitted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        }
    }
}


