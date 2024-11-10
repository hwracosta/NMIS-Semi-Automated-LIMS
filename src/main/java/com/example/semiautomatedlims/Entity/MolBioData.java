package com.example.semiautomatedlims.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "molbio_data")
public class MolBioData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long molId;

    @Column(name = "ld_control_number", nullable = false)
    private String ldControlNumber; // Directly store the control number

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "meat_species_result", nullable = false)
    private String meatSpeciesResult;

    @Column(name = "remarks", nullable = false)
    private String remarks;

    // Getters and Setters
    public String getLdControlNumber() {
        return ldControlNumber;
    }

    public void setLdControlNumber(String ldControlNumber) {
        this.ldControlNumber = ldControlNumber;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
