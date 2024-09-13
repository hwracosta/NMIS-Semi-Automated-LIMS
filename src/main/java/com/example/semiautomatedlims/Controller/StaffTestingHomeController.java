package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffTestingHomeController {
    @GetMapping("/STAFF-TESTINGhome")
    public String staffForgotPassword() {
        return "STAFF-TESTINGhome"; 
    }
}

