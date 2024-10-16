package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TRChemController {

    @GetMapping("/TR-Chem")
    public String showTRChemPage() {
        return "TR-Chem";  
    }
}