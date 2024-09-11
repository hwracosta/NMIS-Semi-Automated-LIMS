package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientResetController {
    
    // Mapping for the CLIENT Reset page
    @GetMapping("/CLIENT-reset")
    public String showClientResetPage() {
        return "CLIENT-reset"; 
    }
}
