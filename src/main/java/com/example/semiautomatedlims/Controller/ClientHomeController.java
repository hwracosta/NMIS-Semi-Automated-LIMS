package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientHomeController {

    @GetMapping("/CLIENT-home")
    public String clientHome(HttpSession session, RedirectAttributes redirectAttributes) {
        Client loggedInClient = (Client) session.getAttribute("loggedInClient");

        if (loggedInClient == null) {
            // Redirect to login if no client is in session
            redirectAttributes.addFlashAttribute("error", "Please log in first.");
            return "redirect:/client-login";
        }

        return "CLIENT-home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();  // Clear the session
        redirectAttributes.addFlashAttribute("message", "Logged out successfully!");
        return "redirect:/client-login";  // Redirect to login page after logout
    }
}