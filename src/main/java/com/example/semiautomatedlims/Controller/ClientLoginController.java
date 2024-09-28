package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientLoginController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Default route ("/") to redirect to client-login page
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/client-login";  // Redirects to client-login
    }

    @GetMapping("/client-login")
    public String loginPage(HttpSession session, Model model) {
        session.invalidate();  // Invalidate any existing session
        model.addAttribute("isLoggedIn", false);  // Add a flag to indicate user is not logged in
        return "CLIENT-LOGIN";  // Render CLIENT-login.html from the templates folder
    }

    // Process login and store user info in session
    @PostMapping("/client-login")
    public String processLogin(@RequestParam String email, @RequestParam String password,
                               HttpSession session, RedirectAttributes redirectAttributes) {
        Client client = clientService.findClientByEmail(email);

        // Check if client exists and password matches
        if (client != null && passwordEncoder.matches(password, client.getPassword())) {
            session.setAttribute("loggedInClient", client);  // Store the client object in session
            return "redirect:/CLIENT-home";  // Redirect to the client dashboard after successful login
        }

        // Invalid login
        redirectAttributes.addFlashAttribute("error", "Invalid credentials, please try again.");
        return "redirect:/client-login";
    }
}