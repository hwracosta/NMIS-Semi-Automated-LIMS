package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestingTestingController {

    @GetMapping("/TESTING-testing")
    public String showReleaseReleasePage() {
        return "TESTING-testing";  
    }
}

