package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Service.ClientFPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/CLIENT-fpwcode")
    public String verifyResetCode(@RequestParam("code") String code,
                                  RedirectAttributes redirectAttributes) {
        String email = clientFPWService.getEmailByCode(code);  // Assuming the code is tied to an email

        if (email != null && clientFPWService.verifyResetCode(code)) {
            // Redirect to reset page with email in flash attributes
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/CLIENT-reset";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired code.");
            return "redirect:/CLIENT-fpwcode";
        }
    }
}
