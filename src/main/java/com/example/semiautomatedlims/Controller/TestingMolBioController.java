package com.example.semiautomatedlims.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestingMolBioController {
    @GetMapping("/TESTING-MolBio")
    public String staffTestingHome() {
        return "TESTING-MolBio"; 
    }
}
