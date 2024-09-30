package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReleaseDatabaseController {

    @GetMapping("/RELEASE-database")
    public String showReleaseReleasePage() {
        return "RELEASE-database";  
    }
}
