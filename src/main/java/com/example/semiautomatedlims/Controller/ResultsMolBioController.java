package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultsMolBioController {

    @GetMapping("/RESULTS-MolBio")
    public String showReleaseReleasePage() {
        return "RESULTS-MolBio";  
    }
}