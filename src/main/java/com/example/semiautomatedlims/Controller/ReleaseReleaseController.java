package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Entity.MolBioData;
import com.example.semiautomatedlims.Service.ReleaseService;
import com.example.semiautomatedlims.Service.TestingChemService;
import com.example.semiautomatedlims.Service.TestingMicrobioService;
import com.example.semiautomatedlims.Service.TestingMolBioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReleaseReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private TestingChemService testingChemService;

    @Autowired
    private TestingMicrobioService testingMicrobioService;

    @Autowired
    private TestingMolBioService testingMolBioService;

    // Show the release page
    @GetMapping("/RELEASE-release")
    public String showReleaseReleasePage(Model model) {
        List<String> statuses = List.of("For Testing", "For Release");
        List<ClientReqForm> processedRequests = releaseService.getProcessedRequests(statuses);
        model.addAttribute("requests", processedRequests);
        return "RELEASE-release";
    }

    // Fetch result details based on test type and clientReqid
    @GetMapping("/api/getResultDetails")
    @ResponseBody
    public Map<String, Object> getResultDetails(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam("testType") String testType) {
        Map<String, Object> resultDetails = new HashMap<>();
        String ldControlNumber = getLdControlNumber(clientReqid);
        resultDetails.put("ldControlNumber", ldControlNumber);

        // Fetch and format data by test type
        switch (testType) {
            case "MolBio" -> resultDetails.put("molbioResults", formatMolBioData(testingMolBioService.findMolBioDataByLdControlNumber(ldControlNumber)));
            case "Chem" -> resultDetails.put("chemResults", formatChemData(testingChemService.findChemDataByLdControlNumber(ldControlNumber)));
            case "Microbio" -> resultDetails.put("microbioResults", formatMicroBioData(testingMicrobioService.findMicroBioDataByLdControlNumber(ldControlNumber)));
            default -> throw new IllegalArgumentException("Invalid test type: " + testType);
        }

        // Update the request status if all tests are complete
        ClientReqForm clientReqForm = releaseService.getRequestById(clientReqid);
        releaseService.updateRequestStatusIfComplete(clientReqForm);

        return resultDetails;
    }

    @PostMapping("/api/submitRequest")
    @ResponseBody
    public ResponseEntity<?> submitRequest(@RequestParam("clientReqid") Long clientReqid) {
        ClientReqForm clientReqForm = releaseService.getRequestById(clientReqid);
        if (clientReqForm != null && "For Release".equals(clientReqForm.getStatus())) {
            releaseService.completeRequest(clientReqForm); // New method to mark as complete
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request not eligible for submission.");
    }

    private String getLdControlNumber(Long clientReqid) {
        ClientReqForm clientReqForm = testingChemService.getRequestDetailsById(clientReqid);
        return clientReqForm != null ? clientReqForm.getLdControlNumber() : "Unknown";
    }

    // Formatting methods for MolBio data
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

    // Formatting methods for Chem data
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

    // Formatting methods for MicroBio data
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