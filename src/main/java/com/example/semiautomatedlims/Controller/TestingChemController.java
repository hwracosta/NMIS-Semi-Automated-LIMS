package com.example.semiautomatedlims.Controller;

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
import com.example.semiautomatedlims.Service.TestingChemService;

@Controller
public class TestingChemController {

    @Autowired
    private TestingChemService testingChemService; // Inject your service here

    @GetMapping("/TESTING-Chem")
    public String staffTestingHome(Model model) {
        List<ClientReqForm> testingRequests = testingChemService.getFilteredRequests(); // Fetch requests
        model.addAttribute("requests", testingRequests); // Add requests to model
        return "TESTING-Chem"; 
    }

    @GetMapping("/api/getChemRequestDetails")
    @ResponseBody
    public String getTestRequestDetails(@RequestParam Long clientReqid) {
        // Fetch the request details from the service
        ClientReqForm requestDetails = testingChemService.getRequestDetailsById(clientReqid);
        
        if (requestDetails == null) {
            return "<p>No details found for this request.</p>"; // Handle not found case
        }

        // Construct the HTML response to display in the popup
        StringBuilder details = new StringBuilder();
        
        details.append("<div class='checklist'>");
        String[] tests = requestDetails.getChemTests() != null ? requestDetails.getChemTests().split(",") : new String[0];
        
        // Use ldControlNumber for checkbox IDs
        String ldControlNumber = requestDetails.getLdControlNumber();
        for (String test : tests) {
            String sanitizedTest = test.trim().replaceAll(" ", "_"); // Sanitize for ID usage
            details.append("<label><input type='checkbox' id='").append(ldControlNumber).append("_").append(sanitizedTest).append("' name='tests' value='").append(test).append("'> ").append(test).append("</label><br>");
        }
        
        details.append("</div>");
        
        return details.toString(); // Return the constructed HTML
    }


    @GetMapping("/api/getChemSampleDetails")
    @ResponseBody
    public ResponseEntity<ClientReqForm> getSampleDetails(@RequestParam Long clientReqid) {
        ClientReqForm sampleDetails = testingChemService.getRequestDetailsById(clientReqid);
   
        if (sampleDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
   
        return ResponseEntity.ok(sampleDetails);
    }    

    @PostMapping("/api/submitChemRequest")
    public ResponseEntity<String> submitChemRequest(@RequestParam Long clientReqid) {
        ClientReqForm request = testingChemService.getRequestDetailsById(clientReqid);
        if (request != null) {
            request.setIsChemTransferred(true); // Add a flag or marker to show it was submitted
            testingChemService.saveRequest(request); // Save the updated request

            return ResponseEntity.ok("Request successfully submitted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        }
    }
}

