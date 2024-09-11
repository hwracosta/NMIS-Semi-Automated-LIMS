package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffLoginController {

    // Mapping for the STAFF login page
    @GetMapping("/STAFF-login")
    public String showStaffLoginPage() {
        return "STAFF-login"; 
    }
}
