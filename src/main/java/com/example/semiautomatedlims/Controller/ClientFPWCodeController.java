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
@Controller
public class ClientFPWCodeController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/CLIENT-fpwcode")
    public String showClientFPWCodePage() {
        return "CLIENT-fpwcode"; // Renders CLIENT-fpwcode.html
    }

    @PostMapping("/CLIENT-verify-fpwcode")
    public String verifyClientFPWCode(@RequestParam("email") String email,
                                      @RequestParam("code") String code,
                                      RedirectAttributes redirectAttributes) {
        Client client = clientService.findClientByEmail(email);

        if (client == null || !client.getResetToken().equals(code)) {
            redirectAttributes.addFlashAttribute("error", "Invalid code or email.");
            return "redirect:/CLIENT-fpwcode";
        }

        if (client.getTokenExpiry().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "The code has expired.");
            return "redirect:/CLIENT-fpw";
        }

        redirectAttributes.addFlashAttribute("message", "Code verified. You may now reset your password.");
        return "redirect:/CLIENT-reset"; // Redirect to the password reset page
    }
}
