package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ReportTestingSummary;
import com.example.semiautomatedlims.Service.ReportTestingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/REPORT-testing")
public class ReportTestingController {

    private final ReportTestingService reportTestingService;

    @Autowired
    public ReportTestingController(ReportTestingService reportTestingService) {
        this.reportTestingService = reportTestingService;
    }

    @GetMapping
    public String getChemicalTestSummary(Model model) {
        model.addAttribute("chemicalSummaries", reportTestingService.getChemicalTestSummaries());
        return "REPORT-testing";  // Updated to match the HTML filename
    }
}

