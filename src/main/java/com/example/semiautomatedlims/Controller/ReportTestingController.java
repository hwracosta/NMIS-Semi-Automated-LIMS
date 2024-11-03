package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.semiautomatedlims.Service.ReportTestingService;
import com.example.semiautomatedlims.Entity.ReportTestingSummary;
import java.util.List;

@Controller
@RequestMapping("/database")
public class ReportTestingController {

    @Autowired
    private ReportTestingService reportTestingService;

    // Method to load the database page
    @GetMapping
    public String showDatabasePage(Model model) {
        // Fetch the list of test summaries from the service layer
        List<ReportTestingSummary> testSummaries = reportTestingService.getAllTestSummaries();

        // Add the data to the model to be accessible in the Thymeleaf template
        model.addAttribute("testSummaries", testSummaries);
        
        return "REPORT-testing"; // Name of the Thymeleaf template (e.g., "database.html")
    }
}
