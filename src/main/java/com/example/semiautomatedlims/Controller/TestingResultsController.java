package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestingResultsController {

    @GetMapping("/TESTING-results")
    public String showReleaseReleasePage() {
        return "TESTING-results";  
    }
}