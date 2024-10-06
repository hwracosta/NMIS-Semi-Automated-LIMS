package com.example.semiautomatedlims.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Import your service
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.TestingChemService;

@Controller
public class TestingChemController {

    @Autowired
    private TestingChemService testingChemService; // Inject your service here

    @GetMapping("/TESTING-Chem")
    public String staffTestingHome(Model model) {
        List<ClientReqForm> testingRequests = testingChemService.getFilteredRequests(); // Fetch requests
        model.addAttribute("requests", testingRequests); // Add requests to model
        return "TESTING-Microbio"; 
    }
}
