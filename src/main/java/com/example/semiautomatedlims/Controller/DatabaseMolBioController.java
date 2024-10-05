package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatabaseMolBioController {

    @GetMapping("/DATABASE-MolBio")
    public String showDatabaseMolBioPage() {
        return "DATABASE-MolBio";  
    }
}