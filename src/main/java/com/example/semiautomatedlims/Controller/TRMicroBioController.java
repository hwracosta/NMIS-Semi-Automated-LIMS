package com.example.semiautomatedlims.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.TestingMicrobioService;

@Controller
public class TRMicroBioController {

    @Autowired
    private TestingMicrobioService testingMicrobioService;

    @GetMapping("/TR-MicroBio")
    public String showTRMicrobioPage(Model model) {
        // Fetch only requests that have a status of "For Testing" and microbio_pending as "pending" and transferred
        List<ClientReqForm> resultRequests = testingMicrobioService.findByMicrobioPending("For Testing", "pending");
        model.addAttribute("requests", resultRequests);
        return "TR-MicroBio";
    }
}