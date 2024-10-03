package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Service.Staff2FAService;
import com.example.semiautomatedlims.Service.StaffService;
import jakarta.servlet.http.HttpSession; // Import the HttpSession class
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

    @Autowired
    private Staff2FAService staff2FAService;

    // Mapping for the STAFF login page
    @GetMapping("/STAFF-login")
    public String showStaffLoginPage() {
        return "STAFF-login";
    }

    @PostMapping("/STAFF-login")
    public String processLogin(@RequestParam String email, 
                                @RequestParam String password, 
                                HttpSession session, // Add HttpSession parameter
                                RedirectAttributes redirectAttributes) {
        Staff staff = staffService.findStaffByEmail(email);
        if (staff != null && staff.getPassword().equals(password)) {
            // Login successful, now send 2FA code
            staff2FAService.sendTwoFactorCodeToEmail(email);

            // Set the testing_section in the session
            session.setAttribute("testingSection", staff.getTestingSection()); // Store testing_section in the session

            // Redirect to the 2FA page
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/STAFF-2FA";
        }

        // Invalid login
        redirectAttributes.addFlashAttribute("error", "Invalid credentials, please try again.");
        return "redirect:/STAFF-login";
    }
}

