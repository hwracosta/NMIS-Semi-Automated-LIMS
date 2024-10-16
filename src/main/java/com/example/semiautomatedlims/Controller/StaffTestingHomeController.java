package com.example.semiautomatedlims.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffTestingHomeController {

    @GetMapping("/STAFF-TESTINGhome")
    public String staffForgotPassword() {
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
        // Retrieve the testing_section from the session
        String testingSection = (String) session.getAttribute("testingSection");
        
        // Check the value of testing_section and redirect accordingly
        if ("molbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/TESTING-MolBio";
        } else if ("microbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/TESTING-Microbio";
        } else if ("chem".equalsIgnoreCase(testingSection)) {
            return "redirect:/TESTING-Chem";
        } else {
            // Redirect to a default page or show an error message if testing_section is not valid
            return "redirect:/default-results";  
        }
    }

    @GetMapping("/TESTING-results")
    public String redirectToTestingResults(HttpSession session) {
        // Retrieve the testing_section from the session
        String testingSection = (String) session.getAttribute("testingSection");
        
        // Check the value of testing_section and redirect accordingly
        if ("molbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/TR-MolBio";
        } else if ("microbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/RESULTS-Microbio";
        } else if ("chem".equalsIgnoreCase(testingSection)) {
            return "redirect:/TR-Chem";
        } else {
            // Redirect to a default page or show an error message if testing_section is not valid
            return "redirect:/default-results";  
        }
    }

    @GetMapping("/TESTING-database")
    public String redirectToTestingDatabase(HttpSession session) {
        // Retrieve the testing_section from the session
        String testingSection = (String) session.getAttribute("testingSection");
        
        // Check the value of testing_section and redirect accordingly
        if ("molbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/DATABASE-MolBio";
        } else if ("microbio".equalsIgnoreCase(testingSection)) {
            return "redirect:/DATABASE-Microbio";
        } else if ("chem".equalsIgnoreCase(testingSection)) {
            return "redirect:/DATABASE-Chem";
        } else {
            // Redirect to a default page or show an error message if testing_section is not valid
            return "redirect:/default-results";  
        }
    }
}


