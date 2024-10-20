package com.example.semiautomatedlims.Controller;

import java.text.SimpleDateFormat;
import java.util.List;

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
import com.example.semiautomatedlims.Service.TestingMicrobioService;

@Controller
public class TestingMicrobioController {

    @Autowired
    private TestingMicrobioService testingMicrobioService; // Inject your service here

    @GetMapping("/TESTING-Microbio")
    public String staffTestingHome(Model model) {
        List<ClientReqForm> testingRequests = testingMicrobioService.getFilteredRequests(); // Fetch requests
        model.addAttribute("requests", testingRequests); // Add requests to model
        return "TESTING-Microbio"; 
    }

    @GetMapping("/api/getMicroBioRequestDetails")
    @ResponseBody
    public String getTestRequestDetails(@RequestParam Long clientReqid) {
        // Fetch the request details from the service
        ClientReqForm requestDetails = testingMicrobioService.getRequestDetailsById(clientReqid);
        
        if (requestDetails == null) {
            return "<p>No details found for this request.</p>"; // Handle not found case
        }

        // Construct the HTML response to display in the popup
        StringBuilder details = new StringBuilder();
        
        details.append("<h4>MicroBio Tests:</h4><div class='checklist'>");
        String[] tests = requestDetails.getMicrobioTests() != null ? requestDetails.getMicrobioTests().split(",") : new String[0];
        
        // Use ldControlNumber for checkbox IDs
        String ldControlNumber = requestDetails.getLdControlNumber();
        for (String test : tests) {
            String sanitizedTest = test.trim().replaceAll(" ", "_"); // Sanitize for ID usage
            details.append("<label><input type='checkbox' id='").append(ldControlNumber).append("_").append(sanitizedTest).append("' name='tests' value='").append(test).append("'> ").append(test).append("</label><br>");
        }
        
        details.append("</div>");
        
        return details.toString(); // Return the constructed HTML
    }


    @GetMapping("/api/getMicroBioSampleDetails")
    @ResponseBody
    public String getSampleDetails(@RequestParam Long clientReqid) {
        ClientReqForm sampleDetails = testingMicrobioService.getRequestDetailsById(clientReqid);
        
        if (sampleDetails == null) {
            return "<p>No sample details found for this request.</p>";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format without timestamp
        String productionDate = dateFormat.format(sampleDetails.getProductionDate());
        String expirationDate = dateFormat.format(sampleDetails.getExpirationDate());

        StringBuilder details = new StringBuilder();
        details.append("<p><strong>Sample Code:</strong> ").append(sampleDetails.getClientSampleCode()).append("</p>");
        details.append("<p><strong>Sample Details:</strong> ").append(sampleDetails.getSampleDetails()).append("</p>");
        details.append("<p><strong>Sample Source:</strong> ").append(sampleDetails.getSampleSource()).append("</p>");
        details.append("<p><strong>Production Date:</strong> ").append(productionDate).append("</p>"); 
        details.append("<p><strong>Expiration Date:</strong> ").append(expirationDate).append("</p>");
        details.append("<p><strong>Weight (grams):</strong> ").append(sampleDetails.getWeightGrams()).append("</p>");

        return details.toString();
    }

    @PostMapping("/api/submitMicrobioRequest")
    public ResponseEntity<String> submitMicrobioRequest(@RequestParam Long clientReqid) {
        ClientReqForm request = testingMicrobioService.getRequestDetailsById(clientReqid);
        if (request != null) {
            request.setIsMicroBioTransferred(true); // Add a flag or marker to show it was submitted
            testingMicrobioService.saveRequest(request); // Save the updated request

            return ResponseEntity.ok("Request successfully submitted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        }
    }
}
