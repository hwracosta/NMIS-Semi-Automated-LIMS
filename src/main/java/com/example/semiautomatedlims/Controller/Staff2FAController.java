package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Staff;
import com.example.semiautomatedlims.Service.Staff2FAService;
import com.example.semiautomatedlims.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Staff2FAController {

    @Autowired
    private Staff2FAService staff2FAService;

    @Autowired
    private StaffService staffService;

    // Display the 2FA page
    @GetMapping("/STAFF-2FA")
    public String show2FAPage() {
        return "STAFF-2FA";
    }

   // Process 2FA code verification (POST to the same URL)
   @PostMapping("/STAFF-2FA")
   public String verifyTwoFactorCode(@RequestParam String email, @RequestParam String code, RedirectAttributes redirectAttributes) {
       if (staff2FAService.verify2FACode(email, code)) {
           // Code is valid, proceed with redirecting to appropriate home page
           Staff staff = staffService.findStaffByEmail(email);
           if (staff.getStaffType().equals("testing")) {
               return "redirect:/STAFF-TESTINGhome";  // Redirect to testing homepage
           } else if (staff.getStaffType().equals("receiving/releasing")) {
               return "redirect:/STAFF-RELEASINGhome";  // Redirect to receiving/releasing homepage
           } else {
               redirectAttributes.addFlashAttribute("error", "Invalid staff type.");
               return "redirect:/STAFF-defaultHome";
           }
       } else {
           // Invalid 2FA code
           redirectAttributes.addFlashAttribute("error", "Invalid 2FA code. Please try again.");
           return "redirect:/STAFF-2FA?email=" + email;  // Redirect back to 2FA page with email
       }
   }
}
