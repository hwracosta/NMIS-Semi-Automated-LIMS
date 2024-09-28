package com.example.semiautomatedlims.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffReleasingHomeController {
    @GetMapping("/STAFF-RELEASINGhome")
    public String staffForgotPassword() {
        return "STAFF-RELEASINGhome"; 
    }

    @GetMapping("/staff-releasing-logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();  // Clear the session
        redirectAttributes.addFlashAttribute("message", "Logged out successfully!");
        return "redirect:/STAFF-login";  // Redirect to login page after logout
    }
}

