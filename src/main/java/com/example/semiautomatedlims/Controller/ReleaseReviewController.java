package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ClientReqFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

   @PostMapping("/release/update-status")
    public String updateStatus(@RequestParam Long clientReqid, @RequestParam String status, RedirectAttributes redirectAttributes) {
        ClientReqForm request = clientReqFormService.findRequestById(clientReqid);
    
        if (request != null) {
            request.setStatus(status); // Update the status
            clientReqFormService.saveClientReqForm(request); // Save the updated request
            redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
        }

        return "redirect:/RELEASE-review"; // Redirect back to the review page
    }
}


