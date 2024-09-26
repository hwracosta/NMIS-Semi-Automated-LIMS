package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReleaseReviewController {
    @GetMapping("/RELEASE-review")
    public String releaseReview() {
        return "RELEASE-review";
    }
}
