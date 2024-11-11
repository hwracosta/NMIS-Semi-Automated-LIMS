package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ReportReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReleaseDatabaseController {

    private final ReportReleaseService reportReleaseService;

    @Autowired
    public ReleaseDatabaseController(ReportReleaseService reportReleaseService) {
        this.reportReleaseService = reportReleaseService;
    }

    @GetMapping("/RELEASE-database")
    public String showReleaseDatabasePage(Model model) {
        // Get the completed requests
        List<ClientReqForm> completedRequests = reportReleaseService.getCompletedRequests();
        
        // Add the list to the model for rendering in the view
        model.addAttribute("completedRequests", completedRequests);
        
        // Return the view name
        return "RELEASE-database";
    }
}
