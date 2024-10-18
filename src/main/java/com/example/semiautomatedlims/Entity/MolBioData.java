package com.example.semiautomatedlims.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "molbio_data")
public class MolBioData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long molId; // Primary key

    @ManyToOne
    @JoinColumn(name = "ld_control_number", referencedColumnName = "ld_control_number", nullable = false)
    private ClientReqForm clientReqForm; // Foreign key reference to ClientReqForm entity

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "meat_species_result", nullable = false)
    private String meatSpeciesResult;

    // Getters and Setters
    public Long getMolId() {
        return molId;
    }

    public void setMolId(Long molId) {
        this.molId = molId;
    }

    public ClientReqForm getClientReqForm() {
        return clientReqForm;
    }

    public void setClientReqForm(ClientReqForm clientReqForm) {
        this.clientReqForm = clientReqForm;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getMeatSpeciesResult() {
        return meatSpeciesResult;
    }

    public void setMeatSpeciesResult(String meatSpeciesResult) {
        this.meatSpeciesResult = meatSpeciesResult;
    }
}
