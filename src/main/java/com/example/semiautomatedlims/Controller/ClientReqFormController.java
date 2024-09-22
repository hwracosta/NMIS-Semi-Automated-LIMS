package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientReqFormController {
    @GetMapping("/CLIENT-reqform")
    public String staffForgotPassword() {
        return "CLIENT-reqform"; 
    }
}


