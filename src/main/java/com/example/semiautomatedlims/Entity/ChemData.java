package com.example.semiautomatedlims.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chem_data")
public class ChemData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chemId;

    @Column(name = "ld_control_number", nullable = false)
    private String ldControlNumber;

    @Column(name = "client_req_id", nullable = false)
    private Long clientReqid;

    @Column(name = "analyte", nullable = false)
    private String analyte;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "detection_limit")
    private String detectionLimit;

    @Column(name = "regulatory_limits")
    private String regulatoryLimits;

    @Column(name = "analysis_date")
    @Temporal(TemporalType.DATE)
    private Date analysisDate;

    // Getters and Setters
    public Long getchemId() {
        return chemId;
    }

    public void setchemId(Long chemId) {
        this.chemId = chemId;
    }

    public String getLdControlNumber() {
        return ldControlNumber;
    }

    public void setLdControlNumber(String ldControlNumber) {
        this.ldControlNumber = ldControlNumber;
    }

    public Long getClientReqid() {
        return clientReqid;
    }

    public void setClientReqid(Long clientReqid) {
        this.clientReqid = clientReqid;
    }

    public String getAnalyte() {
        return analyte;
    }

    public void setAnalyte(String analyte) {
        this.analyte = analyte;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDetectionLimit() {
        return detectionLimit;
    }

    public void setDetectionLimit(String detectionLimit) {
        this.detectionLimit = detectionLimit;
    }

    public String getRegulatoryLimits() {
        return regulatoryLimits;
    }

    public void setRegulatoryLimits(String regulatoryLimits) {
        this.regulatoryLimits = regulatoryLimits;
    }

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Date analysisDate) {
        this.analysisDate = analysisDate;
    }
}
