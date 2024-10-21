package com.example.semiautomatedlims.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.TestingChemService;

@Controller
public class TRChemController {

    @Autowired
    private TestingChemService testingChemService;  // Autowire the service

    @GetMapping("/TR-Chem")
    public String showTRChemPage(Model model) {
        // Fetch only requests that have a status of "For Testing" and chem_pending as "pending" and transferred
        List<ClientReqForm> resultRequests = testingChemService.findByChemPending("For Testing", "pending");
        model.addAttribute("requests", resultRequests);
        return "TR-Chem";
    }
}