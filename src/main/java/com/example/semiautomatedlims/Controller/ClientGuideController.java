package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientGuideController {

    @GetMapping("/CLIENT-guide")
    public String showClientGuidePage() {
        return "CLIENT-guide";  // Returns the CLIENT-guide.html template
    }
}
