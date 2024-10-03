package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultsChemController {

    @GetMapping("/RESULTS-Chem")
    public String showReleaseReleasePage() {
        return "RESULTS-Chem";  
    }
}