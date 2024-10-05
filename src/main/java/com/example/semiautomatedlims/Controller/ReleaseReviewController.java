package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ReleaseService;
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
    private ReleaseService releaseService;

    @GetMapping("/RELEASE-review")
    public String releaseReview(Model model) {
        // Fetch all client requests that are under review
        List<ClientReqForm> requests = releaseService.getRequestsByStatus("Under Review");
        model.addAttribute("requests", requests);
        return "RELEASE-review";
    }

    @PostMapping("/release/update-status")
    public String updateStatus(@RequestParam Long clientReqid, @RequestParam String status, RedirectAttributes redirectAttributes) {
        ClientReqForm request = releaseService.getRequestById(clientReqid); // Use ReleaseService to get the request by ID

        if (request != null) {
            request.setStatus(status); // Update the status
            releaseService.updateRequestStatus(request); // Save the updated request
            redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Request not found.");
        }

        return "redirect:/RELEASE-review"; // Redirect back to the review page
    }
}