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

    @PostMapping("/CLIENT-fpwcode")
    public String verifyResetCode(@RequestParam("code") String code,
                                  RedirectAttributes redirectAttributes) {
        boolean isCodeValid = clientFPWService.verifyResetCode(code);
        if (isCodeValid) {
            // Redirect directly to the reset page once the code is verified
            return "redirect:/CLIENT-reset";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired code.");
            return "redirect:/CLIENT-fpwcode";  // Stay on the same page for reattempt
        }
    }
}
