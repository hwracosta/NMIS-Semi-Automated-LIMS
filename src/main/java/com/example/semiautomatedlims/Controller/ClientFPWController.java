package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Service.ClientService;
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
public class ClientFPWController {

    @Autowired
    private ClientService clientService;

    // Show password reset page when clicking the reset link in email
    @GetMapping("/CLIENT-fpw")
    public String showResetPage(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {
        // Find client by reset token
        Client client = clientService.findByResetToken(token);  // Use findByResetToken instead of the old name

        if (client == null || client.getTokenExpiry().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired password reset token.");
            return "redirect:/CLIENT-fpw";
        }

        // Pass the token to the model to be used in the password reset form
        model.addAttribute("token", token);
        return "client-reset-form";  // Return the view for the password reset form
    }

    // Handle the forgot password request
    @PostMapping("/CLIENT-fpw")
    public String clientForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        // Find client by email
        Client client = clientService.findClientByEmail(email);

        if (client != null) {
            // Generate reset token and set expiration time
            String resetToken = UUID.randomUUID().toString();
            client.setResetToken(resetToken);
            client.setTokenExpiry(LocalDateTime.now().plusHours(1));  // Token expires in 1 hour
            clientService.saveClient(client);

            // Create reset link
            String resetLink = "http://your-domain.com/CLIENT-reset?token=" + resetToken;

            // Send the reset email (email service can be implemented)
            // emailService.sendPasswordResetEmail(client.getEmail(), resetLink);

            redirectAttributes.addFlashAttribute("message", "A password reset link has been sent to your email.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No account found with that email address.");
        }

        return "redirect:/CLIENT-fpw";
    }

    // Handle password reset request
    @PostMapping("/CLIENT-reset")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String newPassword, RedirectAttributes redirectAttributes) {
        // Reset password using the resetPassword method from ClientService
        boolean success = clientService.resetPassword(token, newPassword);

        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired password reset token.");
            return "redirect:/CLIENT-fpw";
        }

        redirectAttributes.addFlashAttribute("message", "Your password has been reset successfully.");
        return "redirect:/CLIENT-login";  // Redirect to login page after successful password reset
    }
}
