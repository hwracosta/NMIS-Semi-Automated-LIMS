package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.StaffFPWService;
import com.example.semiautomatedlims.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffFPWController {

    @Autowired
    private StaffFPWService staffFPWService;

    @Autowired
    private StaffService staffService;  // Ensure StaffService is injected

    @GetMapping("/STAFF-fpw")
    public String showStaffFpwPage() {
        return "STAFF-fpw";
    }

    // Handle email submission to send reset code
    @PostMapping("/STAFF-fpw")
    public String sendResetCode(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        // Check if the staff exists before sending the code
        if (staffService.findStaffByEmail(email) != null) {
            staffFPWService.sendPasswordResetCodeToEmail(email);
            redirectAttributes.addFlashAttribute("message", "A password reset code has been sent to your email.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Email not found.");
        }
        return "redirect:/STAFF-fpwcode";  // Redirect to the page where the user enters the code
    }
}
