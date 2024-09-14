package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Service.StaffService;
import com.example.semiautomatedlims.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

import java.util.UUID;

@Controller
public class StaffFPWController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/STAFF-fpw")
    public String showStaffFpwPage() {
        return "STAFF-fpw";
    }

    // Handle forgot password form submission
    @PostMapping("/staff-fpw")
    public String handleForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Staff staff = staffService.findStaffByEmail(email);
        if (staff == null) {
            redirectAttributes.addFlashAttribute("error", "No account found with this email address.");
            return "redirect:/staff-fpw";
        }

        // Generate reset token
        String token = UUID.randomUUID().toString();
        staff.setResetToken(token);
        staff.setTokenExpiry(LocalDateTime.now().plusMinutes(30)); // Token expires in 30 minutes
        staffService.saveStaff(staff);

        // Send reset token to email
        String resetUrl = "http://localhost:8080/staff-reset?token=" + token;
        emailService.sendSimpleMessage(email, "Reset Your Password",
                "Click the link to reset your password: " + resetUrl);

        redirectAttributes.addFlashAttribute("message", "A reset link has been sent to your email.");
        return "redirect:/staff-login";
    }
}
