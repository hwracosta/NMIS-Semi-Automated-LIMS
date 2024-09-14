package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Service.ClientService;
import com.example.semiautomatedlims.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime; // Import LocalDateTime
import java.util.UUID; // Import UUID

@Controller
public class ClientFPWController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;
    @GetMapping("/CLIENT-fpw")
    public String showResetPage(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {
        // Find client by reset token
        Client client = clientService.findClientByResetToken(token);

        if (client == null || client.getTokenExpiry().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired password reset token.");
            return "redirect:/client-fpw";
        }

        // Pass the token to the model to be used in the password reset form
        model.addAttribute("token", token);
        return "client-reset-form"; // Return the view for the password reset form
    }
    // Handle the forgot password request
    @PostMapping("/client-fpw")
    public String clientForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        // Find client by email
        Client client = clientService.findClientByEmail(email);

        if (client != null) {
            // Generate reset token and set expiration time
            String resetToken = UUID.randomUUID().toString();
            client.setResetToken(resetToken);
            client.setTokenExpiry(LocalDateTime.now().plusHours(1)); // Token expires in 1 hour
            clientService.saveClient(client);

            // Create reset link
            String resetLink = "http://your-domain.com/client-reset?token=" + resetToken;

            // Send the reset email
            emailService.sendPasswordResetEmail(client.getEmail(), resetLink);

            redirectAttributes.addFlashAttribute("message", "A password reset link has been sent to your email.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No account found with that email address.");
        }

        return "redirect:/client-fpw";
    }
}
