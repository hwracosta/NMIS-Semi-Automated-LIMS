package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatabaseChemController {

    @GetMapping("/DATABASE-Chem")
    public String showDatabaseChemPage() {
        return "DATABASE-Chem";  
    }
}