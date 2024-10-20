package com.example.semiautomatedlims.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ReleaseRepository;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    // Method to get requests by status
    public List<ClientReqForm> getRequestsByStatus(String status) {
        return releaseRepository.findByStatus(status);
    }

    // Method to find request by ID
    public ClientReqForm getRequestById(Long clientReqid) {
        Optional<ClientReqForm> requestOptional = releaseRepository.findById(clientReqid);
        return requestOptional.orElse(null);
    }

    // Method to update request status and generate LD Control Number if status is "For Testing"
    public void updateRequestStatus(ClientReqForm clientReqForm) {
        // Check if the status is "For Testing"
        if ("For Testing".equals(clientReqForm.getStatus())) {
            // Generate the LD Control Number
            String ldControlNumber = generateLDControlNumber();
            clientReqForm.setLdControlNumber(ldControlNumber); // Update the LD Control Number
        }
        releaseRepository.save(clientReqForm);
    }

    // Method to get requests by multiple statuses
    public List<ClientReqForm> getProcessedRequests(List<String> statuses) {
        return releaseRepository.findByStatusIn(statuses);
        //return releaseRepository.findByStatusInAndMolbioPending(statuses, "accepted");
    }
    
    private String generateLDControlNumber() {
        // Get the current date (year and month)
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
    
        // Get the maximum series number for the current year and month
        int series = getMaxSeriesForYearMonth(year, month) + 1;
    
        // Log the generated series number for debugging purposes
        System.out.println("Generated LD Control Number series: " + series);
    
        // Format the LD Control Number with 4 digits for the series (e.g., 2024/10/0001)
        return String.format("%d/%02d/%04d", year, month, series);
    }
    

    // Method to get the maximum series number for a given year and month
    private synchronized int getMaxSeriesForYearMonth(int year, int month) {
        try {
            // Query the database to find the maximum series number for the given year and month.
            // The query is designed to extract the max series part of the LD Control Number.
            return releaseRepository.findMaxSeriesForYearMonth(year, month).orElse(0);
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error retrieving max series number for year: " + year + ", month: " + month);
            e.printStackTrace();

            // Return 0 as a fallback to avoid breaking the generation logic
            return 0;
        }
    }
}
