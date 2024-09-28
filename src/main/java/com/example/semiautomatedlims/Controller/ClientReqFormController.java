package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ClientReqFormService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

@Controller
public class ClientReqFormController {

    @Autowired
    private ClientReqFormService clientReqFormService;

    // Display the form page and autofill client details from session
    @GetMapping("/CLIENT-reqform")
    public String clientReqForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Check if client is logged in (from session)
        Client loggedInClient = (Client) session.getAttribute("loggedInClient");

        if (loggedInClient == null) {
            // Redirect to login if no client is in session
            redirectAttributes.addFlashAttribute("error", "Please log in first.");
            return "redirect:/client-login";
        }

        // Add client data to the model for Thymeleaf to use
        model.addAttribute("companyName", loggedInClient.getCompanyName());
        model.addAttribute("representativeName", loggedInClient.getRepresentativeName());
        model.addAttribute("email", loggedInClient.getEmail());
        model.addAttribute("contactNumber", loggedInClient.getContactNumber());
        model.addAttribute("ltoNo", loggedInClient.getLtoNo());
        model.addAttribute("clientClassif", loggedInClient.getClientClassif());
        model.addAttribute("address", loggedInClient.getAddress());

        return "CLIENT-reqform"; // Render the CLIENT-reqform page with autofilled details
    }

    // Process the form submission
    // Process the form submission
    @PostMapping("/CLIENT-reqform")
    public String processClientReqForm(
        @RequestParam String orNo,
        @RequestParam String ldNo,
        @RequestParam String clientSampleCode,
        @RequestParam String sampleDetails,
        @RequestParam String sampleSource,
        @RequestParam Date productionDate,
        @RequestParam Date expirationDate,
        @RequestParam Date samplingDate,
        @RequestParam int weightGrams,
        @RequestParam String purposeTest,
        @RequestParam(required = false) String otherPurposeTest,
        @RequestParam(required = false) String microbioTests,  // Optional fields
        @RequestParam(required = false) String molecTests,
        @RequestParam(required = false) String chemTests,
        @RequestParam String releasingResults,
        @RequestParam(required = false) String regionalOffice,
        @RequestParam String sample_category, // <-- Add this line
        HttpSession session,
        RedirectAttributes redirectAttributes) {

        if ("others".equals(purposeTest) && otherPurposeTest != null && !otherPurposeTest.isEmpty()) {
            purposeTest = otherPurposeTest;
        }

        if ("regional".equals(releasingResults) && regionalOffice != null && !regionalOffice.isEmpty()) {
            releasingResults = regionalOffice;
        }

        // Create a new ClientReqForm entity and set its properties
        ClientReqForm clientReqForm = new ClientReqForm();
        clientReqForm.setOrNo(orNo);
        clientReqForm.setLdNo(ldNo);
        clientReqForm.setClientSampleCode(clientSampleCode);
        clientReqForm.setSampleDetails(sampleDetails);
        clientReqForm.setSampleSource(sampleSource);
        clientReqForm.setProductionDate(productionDate);
        clientReqForm.setExpirationDate(expirationDate);
        clientReqForm.setSamplingDate(samplingDate);
        clientReqForm.setWeightGrams(weightGrams);
        clientReqForm.setPurposeTest(purposeTest);
        clientReqForm.setMicrobioTests(microbioTests);
        clientReqForm.setMolecTests(molecTests);
        clientReqForm.setChemTests(chemTests);
        clientReqForm.setReleasingResults(releasingResults);
        clientReqForm.setSampleCategory(sample_category);
        clientReqFormService.saveClientReqForm(clientReqForm);

        // Redirect after successful submission
        redirectAttributes.addFlashAttribute("message", "Form submitted successfully!");
        return "redirect:/CLIENT-home"; // Redirect to the home page after submission
    }
}
