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
        return "CLIENT-reset";
    }

    @PostMapping("/CLIENT-reset")
    public String resetPassword(@RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes) {
        // Retrieve email from session attributes or flash attributes if necessary
        String email = (String) redirectAttributes.getFlashAttributes().get("email");

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/CLIENT-reset";
        }

        boolean resetSuccessful = clientFPWService.resetPassword(email, passwordEncoder.encode(password));
        if (resetSuccessful) {
            redirectAttributes.addFlashAttribute("message", "Password reset successfully! You can now log in.");
            return "redirect:/CLIENT-login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to reset password.");
            return "redirect:/CLIENT-reset";
        }
    }
}
