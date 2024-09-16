package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.ClientFPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientResetController {

    @Autowired
    private ClientFPWService clientFPWService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/CLIENT-reset")
    public String showClientResetPage() {
        return "CLIENT-reset"; // Return the client password reset page
    }

    // Handle the reset password form submission
    @PostMapping("/CLIENT-reset")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model, RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "CLIENT-reset";  // Stay on the reset page if passwords don't match
        }

        boolean resetSuccessful = clientFPWService.resetPassword(email, passwordEncoder.encode(password));
        if (resetSuccessful) {
            redirectAttributes.addFlashAttribute("message", "Password reset successfully! You can now log in.");
            return "redirect:/CLIENT-login";  // Redirect to login page after successful reset
        } else {
            model.addAttribute("error", "Failed to reset the password. Please try again.");
            return "CLIENT-reset";
        }
    }
}
