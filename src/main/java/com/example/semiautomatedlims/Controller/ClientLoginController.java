package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientLoginController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client-login")
    public String loginPage() {
        return "CLIENT-LOGIN";  // Return client login HTML page
    }

    @PostMapping("/client-login")
    public String processLogin(@RequestParam String email, @RequestParam String password) {
        Client client = clientService.findClientByEmail(email);
        if (client != null && client.getPassword().equals(password)) {
            return "redirect:/client-dashboard";  // Successful login
        }
        return "CLIENT-LOGIN";  // Failed login, reload login page
    }
}
