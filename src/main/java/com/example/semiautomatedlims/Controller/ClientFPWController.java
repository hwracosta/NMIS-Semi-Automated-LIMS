package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientFPWController {
    @GetMapping("/CLIENT-fpw")
    public String clientForgotPassword() {
        return "CLIENT-fpw"; // This will look for a client-fpw.html file in the templates folder
    }
}
