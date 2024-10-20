package com.example.semiautomatedlims.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ClientReqFormRepository;

@Controller
public class TRMolBioController {

    @Autowired
    private ClientReqFormRepository clientReqFormRepository;  // Ensure correct autowiring

    @GetMapping("/TR-MolBio")
    public String showTRMolbioPage(Model model) {
        // Fetch only requests where molbio_pending is 'pending'
        List<ClientReqForm> pendingRequests = clientReqFormRepository.findByMolbioPending("pending");
        model.addAttribute("requests", pendingRequests);
        return "TR-MolBio";
    }
}
