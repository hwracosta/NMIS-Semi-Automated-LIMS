package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
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
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    // Show the reset page
    @GetMapping("/client-reset")
    public String showClientResetPage(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {
        Client client = clientService.findByResetToken(token); // Now this works

        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid password reset token.");
            return "redirect:/client-fpw";
        }

        model.addAttribute("token", token);
        return "CLIENT-reset"; // Render the reset page
    }

    // Handle the password reset form submission
    @PostMapping("/client-reset")
    public String handleClientPasswordReset(@RequestParam("token") String token,
                                            @RequestParam("password") String password,
                                            @RequestParam("confirmPassword") String confirmPassword,
                                            RedirectAttributes redirectAttributes) {
        Client client = clientService.findByResetToken(token);

        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid token.");
            return "redirect:/client-fpw";
        }

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/client-reset?token=" + token;
        }

        // Encode the new password and save the client
        client.setPassword(passwordEncoder.encode(password));
        client.setResetToken(null); // Clear the reset token
        client.setTokenExpiry(null); // Clear the token expiry
        clientService.saveClient(client);

        redirectAttributes.addFlashAttribute("message", "Password has been reset successfully.");
        return "redirect:/client-login"; // Redirect to login page after successful reset
    }
}
