package com.example.semiautomatedlims.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ClientReqFormService;

import jakarta.servlet.http.HttpSession;

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
            // Redirect to log in if no client is in session
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

    @PostMapping("/CLIENT-reqform")
    public String processClientReqForm(
        @RequestParam String orNo,
        @RequestParam String clientSampleCode,
        @RequestParam String sampleDetails,
        @RequestParam String sampleSource,
        @RequestParam Date productionDate,
        @RequestParam Date expirationDate,
        @RequestParam Date samplingDate,
        @RequestParam int weightGrams,
        @RequestParam String purposeTest,
        @RequestParam(required = false) String otherPurposeTest,
        @RequestParam(required = false) List<String> microbioTests, 
        @RequestParam(required = false) String cultureOption,
        @RequestParam(required = false) String otherMicrobioTests, 
        @RequestParam(required = false) String molecTests,
        @RequestParam(required = false) String chemTests,
        @RequestParam String releasingResults,
        @RequestParam(required = false) String regionalOffice,
        @RequestParam String sample_category,
        HttpSession session,
        RedirectAttributes redirectAttributes) {

        // Logic to check if the client is logged in
        Client loggedInClient = (Client) session.getAttribute("loggedInClient");

        if (loggedInClient == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in first.");
            return "redirect:/client-login";
        }

        // Set purpose and releasing results logic
        if ("others".equals(purposeTest) && otherPurposeTest != null && !otherPurposeTest.isEmpty()) {
            purposeTest = otherPurposeTest;
        }

        // Initialize microbioTests
        StringBuilder microbioTestsBuilder = new StringBuilder();

        if (microbioTests != null && !microbioTests.isEmpty()) {
            for (String test : microbioTests) {
                if (microbioTestsBuilder.length() > 0) {
                    microbioTestsBuilder.append(", "); // Add a comma if there's already something in the builder
                }
                microbioTestsBuilder.append(test);

                // If the test is "culture", append the selected culture option
                if ("culture".equals(test) && cultureOption != null && !cultureOption.isEmpty()) {
                    microbioTestsBuilder.append(": ").append(cultureOption);
                }

                // If the test is "others-para", append the specified other test
                if ("others-para".equals(test) && otherMicrobioTests != null && !otherMicrobioTests.isEmpty()) {
                    microbioTestsBuilder.append(": ").append(otherMicrobioTests);
                }
            }
        }

        // Final microbioTests string
        String microbioTestsFinal = microbioTestsBuilder.toString();

        if (microbioTestsFinal.isEmpty()) {
            microbioTestsFinal = null;  // Convert empty strings to null
        }

        if ("regional".equals(releasingResults) && regionalOffice != null && !regionalOffice.isEmpty()) {
            releasingResults = regionalOffice;
        }

        // Create a new ClientReqForm entity and set its properties
        ClientReqForm clientReqForm = new ClientReqForm();
        clientReqForm.setOrNo(orNo);
        clientReqForm.setClientSampleCode(clientSampleCode);
        clientReqForm.setSampleDetails(sampleDetails);
        clientReqForm.setSampleSource(sampleSource);
        clientReqForm.setProductionDate(productionDate);
        clientReqForm.setExpirationDate(expirationDate);
        clientReqForm.setSamplingDate(samplingDate);
        clientReqForm.setWeightGrams(weightGrams);
        clientReqForm.setPurposeTest(purposeTest);
        clientReqForm.setMicrobioTests(microbioTestsFinal);  // Set the final string here
        clientReqForm.setMolecTests(molecTests);
        clientReqForm.setChemTests(chemTests);
        clientReqForm.setReleasingResults(releasingResults);
        clientReqForm.setSampleCategory(sample_category);
        clientReqForm.setClient(loggedInClient);

        // **New Fields**
        clientReqForm.setStatus("Under Review");
        clientReqForm.setSubmitDate(Date.valueOf(LocalDate.now()));

        // Save the form using the service
        clientReqFormService.saveClientReqForm(clientReqForm);

        // Redirect after successful submission
        redirectAttributes.addFlashAttribute("message", "Form submitted successfully!");
        return "redirect:/CLIENT-home";
    }


    //For RELEASE-review pop-up
    @GetMapping("/api/getRequestDetails")
    @ResponseBody
    public ResponseEntity<ClientReqForm> getRequestDetails(@RequestParam Long clientReqid) {
        ClientReqForm requestDetails = clientReqFormService.findRequestById(clientReqid);
        if (requestDetails != null) {
            return ResponseEntity.ok(requestDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
