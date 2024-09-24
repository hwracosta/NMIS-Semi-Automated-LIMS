package com.example.semiautomatedlims.Controller;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Service.ClientReqFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

@Controller
public class ClientReqFormController {

    @Autowired
    private ClientReqFormService clientReqFormService;

    // Display the form page
    @GetMapping("/CLIENT-reqform")
    public String clientReqForm() {
        return "CLIENT-reqform"; // This will render CLIENT-reqform.html
    }

    // Process the form submission
    @PostMapping("/CLIENT-reqform")
    public String processClientReqForm(
            @RequestParam String address,
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
            RedirectAttributes redirectAttributes) {
            
                if ("others".equals(purposeTest) && otherPurposeTest != null && !otherPurposeTest.isEmpty()) {
                    purposeTest = otherPurposeTest;
                }

                if ("regional".equals(releasingResults) && regionalOffice != null && !regionalOffice.isEmpty()) {
                    releasingResults = regionalOffice;
                }

        // Create a new ClientReqForm entity and set its properties
        ClientReqForm clientReqForm = new ClientReqForm();
        clientReqForm.setAddress(address);
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

        // Save the form data to the database
        clientReqFormService.saveClientReqForm(clientReqForm);

        // Redirect after successful submission
        redirectAttributes.addFlashAttribute("message", "Form submitted successfully!");
        return "redirect:/CLIENT-home"; // Redirect to the home page after submission
    }
}
