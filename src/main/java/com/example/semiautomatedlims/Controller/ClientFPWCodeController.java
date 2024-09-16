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
    public String showClientFpwCodePage(@RequestParam(value = "email", required = false) String email, Model model) {
        // Ensure email is passed to the form
        model.addAttribute("email", email);
        return "CLIENT-fpwcode";
    }

    // Handle the form submission for code verification
    @PostMapping("/CLIENT-fpwcode")
    public String verifyResetCode(@RequestParam("email") String email,
                                  @RequestParam("code") String code,
                                  RedirectAttributes redirectAttributes) {
        boolean isCodeValid = clientFPWService.verifyResetCode(email, code);

        if (isCodeValid) {
            // Pass email for the reset page
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("message", "Code verified successfully! You can now reset your password.");
            // Make sure you are redirecting to the correct path
            return "redirect:/CLIENT-reset";  // Check that this path exists
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired code.");
            return "redirect:/CLIENT-fpwcode?email=" + email;  // Stay on the same page if the code is invalid
        }
    }
}
