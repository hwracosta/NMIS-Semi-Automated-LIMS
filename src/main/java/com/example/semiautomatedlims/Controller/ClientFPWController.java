package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientFPWController {

    @GetMapping("/client-fpw")
    public String clientForgotPassword() {
        return "CLIENT-fpw"; // This will render CLIENT-fpw.html in the templates folder
    }
}
