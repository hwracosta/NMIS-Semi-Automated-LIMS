package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TRMicroBioController {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;

    @GetMapping("/TR-MicroBio")
    public String showTRMicrobioPage(Model model) {
        // Fetch only requests with microbio_pending set to 'pending'
        List<ClientReqForm> resultRequests = clientReqFormRepository.findByMicrobioPending("pending");
        model.addAttribute("requests", resultRequests);
        return "TR-MicroBio";
    }
}