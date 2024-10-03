package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultsMicrobioController {

    @GetMapping("/RESULTS-Microbio")
    public String showResultMicrobioPage() {
        return "RESULTS-Microbio";  
    }
}