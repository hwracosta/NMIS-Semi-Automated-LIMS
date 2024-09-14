package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffResetController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Handle reset password form submission
    @PostMapping("/staff-reset")
    public String handlePasswordReset(@RequestParam("token") String token,
                                      @RequestParam("password") String password,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {

        Staff staff = staffService.findByResetToken(token);

        if (staff == null || staff.isTokenExpired()) {
            model.addAttribute("error", "Invalid or expired reset token.");
            return "staff-reset";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "staff-reset";
        }

        // Save the new password
        staff.setPassword(passwordEncoder.encode(password));
        staff.setResetToken(null);
        staff.setTokenExpiry(null);  // Clear token and expiry
        staffService.saveStaff(staff);

        redirectAttributes.addFlashAttribute("message", "Password reset successfully! You can now log in.");
        return "redirect:/staff-login";
    }
}
