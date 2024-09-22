package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.ClientService;
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
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/CLIENT-reset")
    public String showClientResetPage(Model model) {
        // Ensure the email is passed to the reset page if required
        if (!model.containsAttribute("email")) {
            model.addAttribute("email", "");
        }
        return "CLIENT-reset";
    }

    @PostMapping("/CLIENT-reset")
    public String resetPassword(@RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                @RequestParam("email") String email, // Expecting email to come from the form
                                RedirectAttributes redirectAttributes) {

        System.out.println("Email received in resetPassword POST: " + email); // Log the email

        if (email == null || email.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email is missing.");
            return "redirect:/CLIENT-reset";
        }

        redirectAttributes.addFlashAttribute("email", email);

        // Check if the passwords match
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/CLIENT-reset";
        }

        // Call the service to handle password reset
        boolean resetSuccessful = clientService.resetPassword(email, passwordEncoder.encode(newPassword));

        if (resetSuccessful) {
            redirectAttributes.addFlashAttribute("message", "Password reset successfully! You can now log in.");
            return "redirect:/client-login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to reset password.");
            return "redirect:/CLIENT-reset";
        }
    }
}
