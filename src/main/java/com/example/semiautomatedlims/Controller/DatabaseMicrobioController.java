package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatabaseMicrobioController {

    @GetMapping("/DATABASE-Microbio")
    public String showDatabaseMicrobioPage() {
        return "DATABASE-Microbio";  
    }
}
