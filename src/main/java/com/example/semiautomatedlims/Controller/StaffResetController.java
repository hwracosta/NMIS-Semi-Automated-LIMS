package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffResetController {
    
    // Mapping for the STAFF Reset page
    @GetMapping("/STAFF-reset")
    public String showStaffResetPage() {
        return "STAFF-reset"; 
    }
}
