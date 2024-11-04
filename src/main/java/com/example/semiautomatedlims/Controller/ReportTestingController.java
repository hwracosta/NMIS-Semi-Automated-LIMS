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

    @Autowired
    private ReportTestingService reportTestingService;

    @GetMapping
    public String showDatabasePage(Model model) {
        // Retrieve summaries by categories
        List<ReportTestingSummary> microbiologicalTests = reportTestingService.getMicrobiologicalTests();
        List<ReportTestingSummary> molecularBiologyTests = reportTestingService.getMolecularBiologyTests();
        List<ReportTestingSummary> chemicalTests = reportTestingService.getChemicalTests();

        // Add data to the model to be accessed in Thymeleaf
        model.addAttribute("microbiologicalTests", microbiologicalTests);
        model.addAttribute("molecularBiologyTests", molecularBiologyTests);
        model.addAttribute("chemicalTests", chemicalTests);

        return "REPORT-testing"; // Thymeleaf template name
    }
}
