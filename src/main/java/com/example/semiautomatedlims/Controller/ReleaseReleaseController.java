package com.example.semiautomatedlims.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ReleaseService;

@Controller
public class ReleaseReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @GetMapping("/RELEASE-release")
    public String showReleaseReleasePage(Model model) {
        List<String> statuses = List.of("For Testing");
        List<ClientReqForm> processedRequests = releaseService.getProcessedRequests(statuses);
        model.addAttribute("requests", processedRequests);
        return "RELEASE-release";
    }
}
