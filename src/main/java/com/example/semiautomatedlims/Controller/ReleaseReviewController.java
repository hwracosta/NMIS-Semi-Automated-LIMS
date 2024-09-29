package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ClientReqFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReleaseReviewController {

    @Autowired
    private ClientReqFormService clientReqFormService;

    @GetMapping("/RELEASE-review")
    public String releaseReview(Model model) {
        
        // Fetch all client requests
        List<ClientReqForm> requests = clientReqFormService.getAllClientRequests();
        model.addAttribute("requests", requests); 

        return "RELEASE-review";
    }
}

