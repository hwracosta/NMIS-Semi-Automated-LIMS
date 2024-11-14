package com.example.semiautomatedlims.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.semiautomatedlims.Service.StaffFPWService;

@Controller
public class StaffFPWCodeController {

    @Autowired
    private StaffFPWService staffFPWService;

    @GetMapping("/STAFF-fpwcode")
    public String showStaffFpwCodePage() {
        return "STAFF-fpwcode";
    }

    @PostMapping("/STAFF-fpwcode")
    public String verifyResetCode(@RequestParam("code") String code,
                                  RedirectAttributes redirectAttributes) {
        String email = staffFPWService.getEmailByCode(code);  // Assuming the code is tied to an email
    
        if (email != null && staffFPWService.verifyResetCode(code)) {
            // Redirect to reset page with email in flash attributes
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/STAFF-reset";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid reset code. Please make a new request.");
            return "redirect:/STAFF-fpwcode";
        }
    }    
}
