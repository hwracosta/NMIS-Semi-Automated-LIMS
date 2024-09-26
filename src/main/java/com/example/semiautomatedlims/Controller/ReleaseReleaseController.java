package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReleaseReleaseController {

    @GetMapping("/RELEASE-release")
    public String showReleaseReleasePage() {
        return "RELEASE-release";  // Returns the RELEASE-release.html template
    }
}
