package com.example.semiautomatedlims.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Entity.MolBioData;
import com.example.semiautomatedlims.Service.ClientReqFormService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClientTrackReqController {

    @Autowired
    private ClientReqFormService clientReqFormService;

    @GetMapping("/CLIENT-trackreq")
    public String viewClientRequests(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Check if client is logged in (from session)
        Client loggedInClient = (Client) session.getAttribute("loggedInClient");

        if (loggedInClient == null) {
            // Redirect to login if no client is in session
            redirectAttributes.addFlashAttribute("error", "Please log in first.");
            return "redirect:/client-login";
        }

        // Retrieve list of client requests specific to the logged-in client
        List<ClientReqForm> requests = clientReqFormService.getRequestsByClient(loggedInClient);

        // Add the list to the model
        model.addAttribute("requests", requests);
        return "CLIENT-trackreq";  // Return the HTML template name
    }

    @GetMapping("/getRequestDetails")
    @ResponseBody
    public ResponseEntity<ClientReqForm> getRequestDetails(@RequestParam Long requestId) {
        // Fetch the request details based on the request ID
        Optional<ClientReqForm> requestDetails = clientReqFormService.findById(requestId);

        if (requestDetails.isPresent()) {
            return ResponseEntity.ok(requestDetails.get());  // Return the request details as JSON
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if not found
        }
    }

    @GetMapping("/client/api/getResultDetails")
    @ResponseBody
    public Map<String, Object> getResultDetails(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam("testType") String testType) {
        Map<String, Object> resultDetails = new HashMap<>();
        String ldControlNumber = clientReqFormService.getLdControlNumber(clientReqid);
        resultDetails.put("ldControlNumber", ldControlNumber);

        switch (testType) {
            case "MolBio" -> resultDetails.put("molbioResults", formatMolBioData(clientReqFormService.findMolBioDataByLdControlNumber(ldControlNumber)));
            case "Chem" -> resultDetails.put("chemResults", formatChemData(clientReqFormService.findChemDataByLdControlNumber(ldControlNumber)));
            case "Microbio" -> resultDetails.put("microbioResults", formatMicroBioData(clientReqFormService.findMicroBioDataByLdControlNumber(ldControlNumber)));
            default -> throw new IllegalArgumentException("Invalid test type: " + testType);
        }

        return resultDetails;
    }

    // Formatting method for MolBio data
    private List<Map<String, String>> formatMolBioData(List<MolBioData> molBioDataList) {
        List<Map<String, String>> details = new ArrayList<>();
        for (MolBioData data : molBioDataList) {
            Map<String, String> entry = new HashMap<>();
            entry.put("examinationConducted", data.getTestName());
            entry.put("meatSpeciesResult", data.getMeatSpeciesResult());
            entry.put("remarks", data.getRemarks());
            entry.put("analysisDate", data.getAnalysisDate() != null ? data.getAnalysisDate().toString() : "N/A");
            details.add(entry);
        }
        return details;
    }

    // Formatting method for Chem data
    private List<Map<String, String>> formatChemData(List<ChemData> chemDataList) {
        List<Map<String, String>> details = new ArrayList<>();
        for (ChemData data : chemDataList) {
            Map<String, String> entry = new HashMap<>();
            entry.put("analyte", data.getAnalyte());
            entry.put("result", data.getResult());
            entry.put("remarks", data.getRemarks());
            entry.put("detectionLimit", data.getDetectionLimit());
            entry.put("regulatoryLimits", data.getRegulatoryLimits());
            entry.put("analysisDate", data.getAnalysisDate() != null ? data.getAnalysisDate().toString() : "N/A");
            details.add(entry);
        }
        return details;
    }

    // Formatting method for MicroBio data
    private List<Map<String, String>> formatMicroBioData(List<MicroBioData> microBioDataList) {
        List<Map<String, String>> details = new ArrayList<>();
        for (MicroBioData data : microBioDataList) {
            Map<String, String> entry = new HashMap<>();
            entry.put("testConducted", data.getMicTestName());
            entry.put("result", data.getMicResult());
            entry.put("referenceValue", data.getMicRefVal());
            entry.put("remarks", data.getMicRemarks());
            entry.put("analysisDate", data.getAnalysisDate() != null ? data.getAnalysisDate().toString() : "N/A");
            details.add(entry);
        }
        return details;
    }
}