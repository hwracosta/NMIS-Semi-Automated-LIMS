// ClientTrackReqController.java
package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ClientReqFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClientTrackReqController {

    @Autowired
    private ClientReqFormService clientReqFormService;

    @GetMapping("/CLIENT-trackreq")
    public String viewClientRequests(Model model) {
        // Retrieve list of client requests
        List<ClientReqForm> requests = clientReqFormService.getAllClientRequests();
        
        // Add the list to the model
        model.addAttribute("requests", requests);
        return "CLIENT-trackreq";  // Return the HTML template name
    }
}
