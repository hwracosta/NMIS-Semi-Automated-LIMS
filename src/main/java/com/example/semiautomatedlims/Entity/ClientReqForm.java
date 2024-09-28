package com.example.semiautomatedlims.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client_reqform")
public class ClientReqForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientReqid;

    private String address;
    private String orNo;
    private String ldNo;
    private String clientSampleCode;
    private String sampleDetails;
    private String sampleSource;
    private Date productionDate;
    private Date expirationDate;
    private Date samplingDate;
    private int weightGrams;
    private String purposeTest;
    private String microbioTests;
    private String molecTests;
    private String chemTests;
    private String releasingResults;
    private String sampleCategory; 

    // Getters and Setters
    public Long getClientReqid() {
        return clientReqid;
    }

    public void setClientReqid(Long clientReqid) {
        this.clientReqid = clientReqid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrNo() {
        return orNo;
    }

    public void setOrNo(String orNo) {
        this.orNo = orNo;
    }

    public String getLdNo() {
        return ldNo;
    }

    public void setLdNo(String ldNo) {
        this.ldNo = ldNo;
    }

    public String getClientSampleCode() {
        return clientSampleCode;
    }

    public void setClientSampleCode(String clientSampleCode) {
        this.clientSampleCode = clientSampleCode;
    }

    public String getSampleDetails() {
        return sampleDetails;
    }

    public void setSampleDetails(String sampleDetails) {
        this.sampleDetails = sampleDetails;
    }

    public String getSampleSource() {
        return sampleSource;
    }

    public void setSampleSource(String sampleSource) {
        this.sampleSource = sampleSource;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(Date samplingDate) {
        this.samplingDate = samplingDate;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(int weightGrams) {
        this.weightGrams = weightGrams;
    }

    public String getPurposeTest() {
        return purposeTest;
    }

    public void setPurposeTest(String purposeTest) {
        this.purposeTest = purposeTest;
    }

    public String getMicrobioTests() {
        return microbioTests;
    }

    public void setMicrobioTests(String microbioTests) {
        this.microbioTests = microbioTests;
    }

    public String getMolecTests() {
        return molecTests;
    }

    public void setMolecTests(String molecTests) {
        this.molecTests = molecTests;
    }

    public String getChemTests() {
        return chemTests;
    }

    public void setChemTests(String chemTests) {
        this.chemTests = chemTests;
    }

    public String getReleasingResults() {
        return releasingResults;
    }

    public void setReleasingResults(String releasingResults) {
        this.releasingResults = releasingResults;
    }

    public String getSampleCategory() {
        return sampleCategory;
    }

    public void setSampleCategory(String sampleCategory) {
        this.sampleCategory = sampleCategory;
    }
}