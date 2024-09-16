package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.StaffFPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffFPWCodeController {

    @Autowired
    private StaffFPWService staffFPWService;

    @GetMapping("/STAFF-fpwcode")
    public String showStaffFpwCodePage() {
        return "STAFF-fpwcode";
    }

    // Handle the form submission for code verification
    @PostMapping("/STAFF-fpwcode")
    public String verifyResetCode(@RequestParam("email") String email,
                                  @RequestParam("code") String code,
                                  Model model, RedirectAttributes redirectAttributes) {
        boolean isCodeValid = staffFPWService.verifyResetCode(email, code);
        if (isCodeValid) {
            redirectAttributes.addFlashAttribute("message", "Code verified successfully! You can now reset your password.");
            return "redirect:/STAFF-reset";  // Redirect to the password reset page
        } else {
            model.addAttribute("error", "Invalid or expired code.");
            return "STAFF-fpwcode";  // Stay on the same page for reattempt
        }
    }
}
