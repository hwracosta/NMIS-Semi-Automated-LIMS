package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.Staff2FAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Staff2FAController {

    @Autowired
    private Staff2FAService staff2FAService;

    // Display the 2FA page
    @GetMapping("/STAFF-2FA")
    public String show2FAPage() {
        return "STAFF-2FA";
    }

    // Send the 2FA code to the staff's email
    @PostMapping("/STAFF-send-2FA")
    public String send2FACode(@RequestParam String email, RedirectAttributes redirectAttributes) {
        boolean emailSent = staff2FAService.send2FACode(email);
        if (emailSent) {
            redirectAttributes.addFlashAttribute("message", "2FA code sent to your email.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to send 2FA code. Please check your email.");
        }
        return "redirect:/STAFF-2FA";
    }

    // Verify the entered 2FA code
    @PostMapping("/STAFF-verify-2FA")
    public String verify2FACode(@RequestParam String email, @RequestParam String code, RedirectAttributes redirectAttributes) {
        boolean isVerified = staff2FAService.verify2FACode(email, code);
        if (isVerified) {
            redirectAttributes.addFlashAttribute("message", "2FA verification successful!");
            return "redirect:/STAFF-dashboard"; // Redirect to dashboard on success
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid 2FA code. Please try again.");
            return "redirect:/STAFF-2FA"; // Redirect back to 2FA page on failure
        }
    }
}
