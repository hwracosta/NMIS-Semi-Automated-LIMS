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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
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

    // Method to show the release page
    @GetMapping("/RELEASE-release")
    public String showReleaseReleasePage(Model model) {
        List<String> statuses = List.of("For Testing");
        List<ClientReqForm> processedRequests = releaseService.getProcessedRequests(statuses);
        model.addAttribute("requests", processedRequests);
        return "RELEASE-release";
    }

    // Method to fetch result details based on test type and clientReqid
    @GetMapping("/api/getResultDetails")
    @ResponseBody
    public Map<String, Object> getResultDetails(@RequestParam("clientReqid") Long clientReqid,
                                                @RequestParam("testType") String testType) {
        Map<String, Object> resultDetails = new HashMap<>();

        // Get the LD Control Number
        String ldControlNumber = getLdControlNumber(clientReqid);
        resultDetails.put("ldControlNumber", ldControlNumber);

        // Fetch data based on test type
        switch (testType) {
            case "MolBio" -> {
                List<Map<String, String>> molBioDataList = formatMolBioData(testingMolBioService.findMolBioDataByLdControlNumber(ldControlNumber));
                resultDetails.put("molbioResults", molBioDataList);
            }
            case "Chem" -> {
                List<Map<String, String>> chemDataList = formatChemData(testingChemService.findChemDataByLdControlNumber(ldControlNumber));
                resultDetails.put("chemResults", chemDataList);
            }
            case "Microbio" -> {
                List<Map<String, String>> microBioDataList = formatMicroBioData(testingMicrobioService.findMicroBioDataByLdControlNumber(ldControlNumber));
                resultDetails.put("microbioResults", microBioDataList);
            }
        }

        return resultDetails;
    }

    private String getLdControlNumber(Long clientReqid) {
        ClientReqForm clientReqForm = testingChemService.getRequestDetailsById(clientReqid); // Fetch using any service
        return clientReqForm != null ? clientReqForm.getLdControlNumber() : "Unknown";
    }

    private List<Map<String, String>> formatMolBioData(List<MolBioData> molBioDataList) {
        List<Map<String, String>> details = new ArrayList<>();
        for (MolBioData data : molBioDataList) {
            Map<String, String> entry = new HashMap<>();
            entry.put("examinationConducted", data.getTestName());
            entry.put("meatSpeciesResult", data.getMeatSpeciesResult());
            details.add(entry);
        }
        return details;
    }


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


    private List<Map<String, String>> formatMicroBioData(List<MicroBioData> microBioDataList) {
        List<Map<String, String>> details = new ArrayList<>();
        for (MicroBioData data : microBioDataList) {
            Map<String, String> entry = new HashMap<>();
            entry.put("testConducted", data.getMicTestName());
            entry.put("result", data.getMicResult());
            entry.put("referenceValue", data.getMicRefVal());
            entry.put("remarks", data.getMicRemarks());
            details.add(entry);
        }
        return details;
    }
}