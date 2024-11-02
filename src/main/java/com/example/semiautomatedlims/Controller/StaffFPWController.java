package com.example.semiautomatedlims.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.semiautomatedlims.Service.StaffFPWService;

@Controller
public class StaffFPWController {

    @Autowired
    private StaffFPWService staffFPWService;

    @GetMapping("/STAFF-fpw")
    public String showStaffFpwPage() {
        return "STAFF-fpw";
    }

    @PostMapping("/STAFF-fpw")
    public String sendResetCode(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        if (!staffFPWService.sendPasswordResetCodeToEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "This email is not registered in our system.");
            return "redirect:/STAFF-fpw";  // Stay on the same page if email does not exist
        }
        
        redirectAttributes.addFlashAttribute("message", "A password reset code has been sent to your email.");
        return "redirect:/STAFF-fpwcode";  // Redirect to the page where the user enters the code
    }

}