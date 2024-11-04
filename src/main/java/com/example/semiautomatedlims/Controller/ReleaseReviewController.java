package com.example.semiautomatedlims.Controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ReleaseService;

@Controller
public class ReleaseReviewController {

    @Autowired
    private ReleaseService releaseService;

    @GetMapping("/RELEASE-review")
    public String releaseReview(Model model) {
        
        List<ClientReqForm> requests = releaseService.getRequestsByStatus("Under Review");
        model.addAttribute("requests", requests);
        return "RELEASE-review";
    }

    @PostMapping("/release/update-status")
    public String updateStatus(@RequestParam Long clientReqid, @RequestParam String status, RedirectAttributes redirectAttributes) {
        ClientReqForm request = releaseService.getRequestById(clientReqid); 

        if (request != null) {
            request.setStatus(status); 
            releaseService.updateRequestStatus(request); 
            redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
        }

        return "redirect:/RELEASE-review"; 
    }

    @GetMapping("/api/getClientDetails")
    @ResponseBody
    public Map<String, Object> getClientDetails(@RequestParam Long clientReqid) {
        ClientReqForm request = releaseService.getRequestById(clientReqid);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (request != null && request.getClient() != null) {
            Map<String, Object> clientDetails = new HashMap<>();
            clientDetails.put("companyName", request.getClient().getCompanyName());
            clientDetails.put("contactName", request.getClient().getRepresentativeName());
            clientDetails.put("contactEmail", request.getClient().getEmail());
            clientDetails.put("phoneNumber", request.getClient().getContactNumber());
            clientDetails.put("address", request.getClient().getAddress());
            clientDetails.put("sampleCategory", capitalize(request.getSampleCategory()));
            clientDetails.put("sampleSource", capitalize(request.getSampleSource()));
            clientDetails.put("sampleCode", request.getClientSampleCode());
            clientDetails.put("sampleDetails", request.getSampleDetails());
            clientDetails.put("productionDate", request.getProductionDate() != null ? dateFormat.format(request.getProductionDate()) : null);
            clientDetails.put("expirationDate", request.getExpirationDate() != null ? dateFormat.format(request.getExpirationDate()) : null);
            clientDetails.put("samplingDate", request.getSamplingDate() != null ? dateFormat.format(request.getSamplingDate()) : null);
            clientDetails.put("weightGrams", request.getWeightGrams());
            clientDetails.put("purposeTest", capitalize(request.getPurposeTest()));
            clientDetails.put("molecTests", capitalize(request.getMolecTests()));
            clientDetails.put("microbioTests", capitalize(request.getMicrobioTests()));
            clientDetails.put("chemTests", capitalize(request.getChemTests()));
            return clientDetails;
        } else {
            return Map.of("error", "Client details not found");
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
}

