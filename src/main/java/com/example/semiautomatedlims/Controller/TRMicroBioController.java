package com.example.semiautomatedlims.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;

@Controller
public class TRMicroBioController {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;  // Ensure correct autowiring

    @GetMapping("/TR-MicroBio")
    public String showTRMicrobioPage(Model model) {
        // Fetch only requests that have been "transferred" from TESTING-MolBio
        List<ClientReqForm> resultRequests = clientReqFormRepository.findByIsMicroBioTransferredTrue();
        model.addAttribute("requests", resultRequests);
        return "TR-MicroBio";
    }
}
