// Modified ReleaseService.java
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
        if ("For Testing".equals(clientReqForm.getStatus())) {
            String ldControlNumber = generateLDControlNumber();
            clientReqForm.setLdControlNumber(ldControlNumber); // Update the LD Control Number
        }
        releaseRepository.save(clientReqForm);
    }

    // Method to get requests by multiple statuses
    public List<ClientReqForm> getProcessedRequests(List<String> statuses) {
        return releaseRepository.findByStatusIn(statuses);
    }

    // Optimized method to check if all tests are complete by verifying database flags
    public boolean areAllTestsComplete(ClientReqForm clientReqForm) {
        System.out.println("Checking if all requested tests are complete for Request ID: " + clientReqForm.getClientReqid());

        // Check each test type based on *_transferred and *_pending columns
        if (Boolean.TRUE.equals(clientReqForm.getIsMolBioTransferred()) && !"accepted".equalsIgnoreCase(clientReqForm.getMolbioPending())) {
            return false;
        }
        if (Boolean.TRUE.equals(clientReqForm.getIsChemTransferred()) && !"accepted".equalsIgnoreCase(clientReqForm.getChemPending())) {
            return false;
        }
        if (Boolean.TRUE.equals(clientReqForm.getIsMicroBioTransferred()) && !"accepted".equalsIgnoreCase(clientReqForm.getMicrobioPending())) {
            return false;
        }

        System.out.println("All requested tests are complete for Request ID: " + clientReqForm.getClientReqid());
        return true;
    }

    // Method to update request status if all relevant test results are complete
    public void updateRequestStatusIfComplete(ClientReqForm clientReqForm) {
        if ("For Testing".equals(clientReqForm.getStatus()) && areAllTestsComplete(clientReqForm)) {
            System.out.println("Updating status to 'For Release' for Request ID: " + clientReqForm.getClientReqid());
            clientReqForm.setStatus("For Release");
            releaseRepository.save(clientReqForm);
        } else {
            System.out.println("Request ID " + clientReqForm.getClientReqid() + " not eligible for 'For Release' status update.");
        }
    }

    private String generateLDControlNumber() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int series = getMaxSeriesForYearMonth(year, month) + 1;
        System.out.println("Generated LD Control Number series: " + series);
        return String.format("%d/%02d/%04d", year, month, series);
    }

    private synchronized int getMaxSeriesForYearMonth(int year, int month) {
        try {
            return releaseRepository.findMaxSeriesForYearMonth(year, month).orElse(0);
        } catch (Exception e) {
            System.err.println("Error retrieving max series number for year: " + year + ", month: " + month);
            e.printStackTrace();
            return 0;
        }
    }
}
