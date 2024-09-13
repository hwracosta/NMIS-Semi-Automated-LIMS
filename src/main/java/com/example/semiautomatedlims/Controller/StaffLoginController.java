package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffLoginController {

    @Autowired
    private StaffService staffService;

    // Mapping for the STAFF login page
    @GetMapping("/STAFF-login")
    public String showStaffLoginPage() {
        return "STAFF-login";
    }

    // Process login form
    @PostMapping("/STAFF-login")
    public String processLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        Staff staff = staffService.findStaffByEmail(email);
        if (staff != null && staff.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            return "redirect:/staff-home";  // Redirect to staff dashboard after successful login
        }
        redirectAttributes.addFlashAttribute("error", "Invalid credentials, please try again.");
        return "redirect:/STAFF-login";  // Return to the login page on failure
    }
}
