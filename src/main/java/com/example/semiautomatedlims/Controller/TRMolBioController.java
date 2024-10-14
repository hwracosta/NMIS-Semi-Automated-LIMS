package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TRMolBioController {

    @GetMapping("/TR-MolBio")
    public String showTRMolbioPage() {
        return "TR-MolBio";  
    }
}