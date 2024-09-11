package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffFPWController {
    @GetMapping("/STAFF-fpw")
    public String staffForgotPassword() {
        return "STAFF-fpw"; // This will look for a staff-fpw.html file in the templates folder
    }
}
