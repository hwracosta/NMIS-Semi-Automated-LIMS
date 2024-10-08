package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ClientReqFormService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Optional;

@Controller
public class ClientTrackReqController {

    @Autowired
    private ClientReqFormService clientReqFormService;

    @GetMapping("/CLIENT-trackreq")
    public String viewClientRequests(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Check if client is logged in (from session)
        Client loggedInClient = (Client) session.getAttribute("loggedInClient");

        if (loggedInClient == null) {
            // Redirect to login if no client is in session
            redirectAttributes.addFlashAttribute("error", "Please log in first.");
            return "redirect:/client-login";
        }

        // Retrieve list of client requests specific to the logged-in client
        List<ClientReqForm> requests = clientReqFormService.getRequestsByClient(loggedInClient);

        // Add the list to the model
        model.addAttribute("requests", requests);
        return "CLIENT-trackreq";  // Return the HTML template name
    }

    // Add the method to fetch request details by ID
    @GetMapping("/getRequestDetails")
    @ResponseBody
    public ResponseEntity<ClientReqForm> getRequestDetails(@RequestParam Long requestId) {
        // Fetch the request details based on the request ID
        Optional<ClientReqForm> requestDetails = clientReqFormService.findById(requestId);

        if (requestDetails.isPresent()) {
            return ResponseEntity.ok(requestDetails.get());  // Return the request details as JSON
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if not found
        }
    }
}
