package com.example.semiautomatedlims.Controller;

import java.util.List;

import com.example.semiautomatedlims.Service.TestingMolBioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.semiautomatedlims.Entity.ClientReqForm;

@Controller
public class TRMolBioController {

    @Autowired
    private TestingMolBioService testingMolBioService;

    @GetMapping("/TR-MolBio")
    public String showTRMolbioPage(Model model) {
        // Fetch only requests that have a status of "For Testing" and molbio_pending as "pending"
        List<ClientReqForm> resultRequests = testingMolBioService.findByMolbioPending("For Testing", "pending");
        model.addAttribute("requests", resultRequests);
        return "TR-MolBio";
    }
}
