package com.example.semiautomatedlims.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "microbio_data")
public class MicroBioData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long micId;

    @Column(name = "ld_control_number", nullable = false)
    private String ldControlNumber; // Directly store the control number

    @Column(name = "test_name", nullable = false)
    private String micTestName;

    @Column(name = "result", nullable = false)
    private String micResult;

    @Column(name = "ref_value")
    private String micRefVal;

    @Column(name = "remarks")
    private String micRemarks;

    @Column(name = "analysis_date")
    @Temporal(TemporalType.DATE)
    private Date analysisDate;

    // Getters and Setters
    public String getLdControlNumber() {
        return ldControlNumber;
    }

    public void setLdControlNumber(String ldControlNumber) {
        this.ldControlNumber = ldControlNumber;
    }

    public String getMicTestName() {
        return micTestName;
    }

    public void setMicTestName(String micTestName) {
        this.micTestName = micTestName;
    }

    public String getMicResult() {
        return micResult;
    }

    public void setMicResult(String micResult) {
        this.micResult = micResult;
    }

    public String getMicRefVal() {
        return micRefVal;
    }

    public void setMicRefVal(String micRefVal) {
        this.micRefVal = micRefVal;
    }

    public String getMicRemarks() {
        return micRemarks;
    }

    public void setMicRemarks(String micRemarks) {
        this.micRemarks = micRemarks;
    }

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Date analysisDate) {
        this.analysisDate = analysisDate;
    }
}
