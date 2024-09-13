package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffReleasingHomeController {
    @GetMapping("/STAFF-RELEASINGhome")
    public String staffForgotPassword() {
        return "STAFF-RELEASINGhome"; 
    }
}

