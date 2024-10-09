package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    }

    // Method to generate LD Control Number
    private String generateLDControlNumber() {
        // Get the current year and month
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        // Get the maximum series number for the current year and month
        int series = getMaxSeriesForYearMonth(year, month) + 1;

        // Format the LD Control Number
        return String.format("%d/%02d/%d", year, month, series);
    }

    // Method to get the maximum series number for a given year and month
    private int getMaxSeriesForYearMonth(int year, int month) {
        // Query the database to find the maximum series number for the given year and month
        return releaseRepository.findMaxSeriesForYearMonth(year, month).orElse(0);
    }
}
