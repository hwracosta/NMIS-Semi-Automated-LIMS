package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.ClientFPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientFPWCodeController {

    @Autowired
    private ClientFPWService clientFPWService;

    @GetMapping("/CLIENT-fpwcode")
    public String showClientFpwCodePage() {
        return "CLIENT-fpwcode";
    }

    // Handle the form submission for code verification
    @PostMapping("/CLIENT-fpwcode")
    public String verifyResetCode(@RequestParam("email") String email,
                                  @RequestParam("code") String code,
                                  Model model, RedirectAttributes redirectAttributes) {
        boolean isCodeValid = clientFPWService.verifyResetCode(email, code);
        if (isCodeValid) {
            redirectAttributes.addFlashAttribute("email", email);  // Pass the email for the reset form
            redirectAttributes.addFlashAttribute("message", "Code verified successfully! You can now reset your password.");
            return "redirect:/CLIENT-reset";  // Redirect to the password reset page
        } else {
            model.addAttribute("error", "Invalid or expired code.");
            return "CLIENT-fpwcode";  // Stay on the same page for reattempt
        }
    }
}
