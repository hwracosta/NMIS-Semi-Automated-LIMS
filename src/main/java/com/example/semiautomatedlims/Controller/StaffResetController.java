package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.StaffFPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffResetController {

    @Autowired
    private StaffFPWService staffFPWService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/STAFF-reset")
    public String showStaffResetPage() {
        return "STAFF-reset";
    }

    // Handle the reset password form submission
    @PostMapping("/STAFF-reset")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model, RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "STAFF-reset";
        }

        boolean resetSuccessful = staffFPWService.resetPassword(email, passwordEncoder.encode(password));
        if (resetSuccessful) {
            redirectAttributes.addFlashAttribute("message", "Password reset successfully! You can now log in.");
            return "redirect:/staff-login";  // Redirect to login page after successful reset
        } else {
            model.addAttribute("error", "Failed to reset the password. Please try again.");
            return "STAFF-reset";
        }
    }
}
