package com.example.semiautomatedlims.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class StaffTestingHomeController {

    @GetMapping("/STAFF-TESTINGhome")
    public String staffTestingHome(HttpSession session, Model model) {
        String testingSection = (String) session.getAttribute("testingSection");
        
        // Format the testingSection based on its value
        if (testingSection != null) {
            switch (testingSection.toLowerCase()) {
                case "molbio":
                    testingSection = "Molecular Biology";
                    break;
                case "microbio":
                    testingSection = "Microbiology";
                    break;
                case "chem":
                    testingSection = "Chemistry";
                    break;
                default:
                    testingSection = "Unknown"; // Handle unexpected values
                    break;
            }
        }
        
        model.addAttribute("testingSection", testingSection);
        return "STAFF-TESTINGhome"; 
    }



    @GetMapping("/staff-testing-logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();  // Clear the session
        redirectAttributes.addFlashAttribute("message", "Logged out successfully!");
        return "redirect:/STAFF-login";  // Redirect to login page after logout
    }

    @GetMapping("/TESTING-testing")
    public String redirectToTestingTesting(HttpSession session) {
        // Retrieve the testingSection from the session
        String testingSection = (String) session.getAttribute("testingSection");
        
        // Check the value of testingSection and redirect accordingly
        if ("molbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/TESTING-MolBio";
        } else if ("microbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/TESTING-Microbio";
        } else if ("chem".equalsIgnoreCase(testingSection)) {
            return "redirect:/TESTING-Chem";
        } else {
            return "redirect:/default-results";  
        }
    }

    @GetMapping("/TESTING-results")
    public String redirectToTestingResults(HttpSession session) {
        String testingSection = (String) session.getAttribute("testingSection");
        
        if ("molbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/TR-MolBio";
        } else if ("microbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/TR-MicroBio";
        } else if ("chem".equalsIgnoreCase(testingSection)) {
            return "redirect:/TR-Chem";
        } else {
            return "redirect:/default-results";  
        }
    }

    @GetMapping("/TESTING-database")
    public String redirectToTestingDatabase(HttpSession session) {
        // Directly redirect to the REPORT-testing page
        return "redirect:/REPORT-testing";  
    }
    
}



