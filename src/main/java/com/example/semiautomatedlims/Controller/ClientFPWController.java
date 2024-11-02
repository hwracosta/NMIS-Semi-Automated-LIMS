package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.ClientFPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientFPWController {

    @Autowired
    private ClientFPWService clientFPWService;

    @GetMapping("/CLIENT-fpw")
    public String showClientFpwPage() {
        return "CLIENT-fpw";
    }

    // Handle email submission to send reset code
    @PostMapping("/CLIENT-fpw")
    public String sendResetCode(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        if (!clientFPWService.sendPasswordResetCodeToEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "This email is not registered in our system.");
            return "redirect:/CLIENT-fpw";  // Stay on the same page if email does not exist
        }
        
        redirectAttributes.addFlashAttribute("message", "A password reset code has been sent to your email.");
        return "redirect:/CLIENT-fpwcode";  // Redirect to the page where the user enters the code
    }
}
