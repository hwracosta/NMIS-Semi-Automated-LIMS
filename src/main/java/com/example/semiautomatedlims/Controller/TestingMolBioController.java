package com.example.semiautomatedlims.Controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    // Method to fetch test request details
    @GetMapping("/api/getTestRequestDetails")
    @ResponseBody
    public String getTestRequestDetails(@RequestParam Long clientReqid) {
        // Fetch the request details from the service
        ClientReqForm requestDetails = testingMolBioService.getRequestDetailsById(clientReqid);
        
        if (requestDetails == null) {
            return "<p>No details found for this request.</p>"; // Handle not found case
        }

        // Construct the HTML response to display in the popup
        StringBuilder details = new StringBuilder();
        
        //details.append("<p><strong>Control Number:</strong> ").append(requestDetails.getOrNo()).append("</p>");
        details.append("<p><strong>MolBio Tests:</strong> ").append(requestDetails.getMolecTests() != null ? requestDetails.getMolecTests() : "N/A").append("</p>");
        
        return details.toString(); // Return the constructed HTML
    }

    @GetMapping("/api/getSampleDetails")
    @ResponseBody
    public String getSampleDetails(@RequestParam Long clientReqid) {
        ClientReqForm sampleDetails = testingMolBioService.getRequestDetailsById(clientReqid);
        
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
}

