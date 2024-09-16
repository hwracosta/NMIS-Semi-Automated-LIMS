package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientLoginController {

    @Autowired
    private ClientService clientService;

    // Default route ("/") to redirect to client-login page
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/client-login";  // Redirects to client-login
    }

    // Display login page
    @GetMapping("/client-login")
    public String loginPage() {
        return "CLIENT-LOGIN";  // Renders CLIENT-login.html from the templates folder
    }

    // Process login form
    @PostMapping("/client-login")
    public String processLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        Client client = clientService.findClientByEmail(email);
        if (client != null && client.getPassword().equals(password)) {
            return "redirect:/CLIENT-home";  // Redirect to the client dashboard after successful login
        }
        // Invalid login
        redirectAttributes.addFlashAttribute("error", "Invalid credentials, please try again.");
        return "redirect:/client-login";
    }
}
