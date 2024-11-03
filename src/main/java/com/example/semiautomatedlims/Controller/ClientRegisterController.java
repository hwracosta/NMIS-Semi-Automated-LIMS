package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
public class ClientRegisterController {

    @Autowired
    private ClientService clientService;

    // Display registration page
    @GetMapping("/client-register")
    public String registerPage() {
        return "CLIENT-register";  // Renders CLIENT-register.html from the templates folder
    }

    @PostMapping("/CLIENT-register")
    public String processRegister(
            @RequestParam String companyName,
            @RequestParam String contactNumber,
            @RequestParam String representativeName,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam(required = false) String ltoNumber,
            @RequestParam String clientClassif,
            @RequestParam(required = false) String otherClientType,
            RedirectAttributes redirectAttributes,
            Model model) { // Add Model parameter
    
        if (clientService.isEmailInUse(email)) {
            model.addAttribute("emailError", "This email is already in use."); // Add error message to model
            return "CLIENT-register"; // Return to the registration page
        }
    
        if ("others".equals(clientClassif) && otherClientType != null && !otherClientType.isEmpty()) {
            clientClassif = otherClientType;
        }
    
        // Create a new client entity
        Client newClient = new Client();
        newClient.setCompanyName(companyName);
        newClient.setContactNumber(contactNumber);
        newClient.setRepresentativeName(representativeName);
        newClient.setPassword(password); // Hash the password in production
        newClient.setEmail(email);
        newClient.setAddress(address);
        newClient.setLtoNo(ltoNumber); // Set LTO No.
        newClient.setClientClassif(clientClassif); // Set client classification
    
        // Save client to the database
        clientService.saveClient(newClient);
    
        // Redirect to login page after successful registration
        redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
        return "redirect:/client-login"; // Redirect to client login page
    }
    
}
