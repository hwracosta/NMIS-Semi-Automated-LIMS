package com.example.semiautomatedlims.Entity;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "client_reqform")
public class ClientReqForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientReqid;

    @Column(name = "or_no", nullable = false)
    private String orNo;

    @Column(name = "client_sample_code", nullable = false)
    private String clientSampleCode;

    @Column(name = "sample_details", nullable = false)
    private String sampleDetails;

    @Column(name = "sample_source", nullable = false)
    private String sampleSource;

    @Column(name = "production_date", nullable = false)
    private Date productionDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "sampling_date", nullable = false)
    private Date samplingDate;

    @Column(name = "weight_grams", nullable = false)
    private double weightGrams;

    @Column(name = "purpose_test", nullable = false)
    private String purposeTest;

    @Column(name = "microbio_tests", length = 500)
    private String microbioTests;

    @Column(name = "molec_tests", length = 500)
    private String molecTests;

    @Column(name = "chem_tests", length = 500)
    private String chemTests;

    @Column(name = "releasing_results", nullable = false)
    private String releasingResults;

    @Column(name = "sample_category", nullable = false)
    private String sampleCategory;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "submit_date")
    private Date submitDate;

    @Column(name = "molbio_transferred", nullable = true)
    private Boolean isMolBioTransferred = false;
 
    @Column(name = "microbio_transferred", nullable = true)
    private Boolean isMicroBioTransferred = false;
 
    @Column(name = "chem_transferred", nullable = true)
    private Boolean isChemTransferred = false;

    @Column(name = "ld_control_number", nullable = true)
    private String ldControlNumber;

    @Column(name = "molbio_pending", nullable = false)
    private String molbioPending = "pending";

    @Column(name = "chem_pending", nullable = false)
    private String chemPending = "pending"; // Default value is 'pending'

    @Column(name = "microbio_pending", nullable = false)
    private String microbioPending = "pending";  // Default value as 'pending'

    // Getters and Setters

    public Long getClientReqid() {
        return clientReqid;
    }

    public void setClientReqid(Long clientReqid) {
        this.clientReqid = clientReqid;
    }

    public String getOrNo() {
        return orNo;
    }

    public void setOrNo(String orNo) {
        this.orNo = orNo;
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

    public double getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(double weightGrams) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getLdControlNumber() {
        return ldControlNumber;
    }

    public void setLdControlNumber(String ldControlNumber) {
        this.ldControlNumber = ldControlNumber;
    }
    
    public Boolean getIsMolBioTransferred() {
        return isMolBioTransferred;
    }

    public void setIsMolBioTransferred(Boolean isMolBioTransferred) {
        this.isMolBioTransferred = isMolBioTransferred;
    }

    public Boolean getIsMicroBioTransferred() {
        return isMicroBioTransferred;
    }

    public void setIsMicroBioTransferred(Boolean isMicroBioTransferred) {
        this.isMicroBioTransferred = isMicroBioTransferred;
    }

    public Boolean getIsChemTransferred() {
        return isChemTransferred;
    }

    public void setIsChemTransferred(Boolean isChemTransferred) {
        this.isChemTransferred = isChemTransferred;
    }

    // New method to extract the list of molecular tests
    public List<String> getMolecExaminations() {
        if (this.molecTests != null && !this.molecTests.isEmpty()) {
            // Assuming molecTests is a comma-separated string
            return Arrays.asList(this.molecTests.split("\\s*,\\s*"));
        } else {
            return List.of(); // Return an empty list if no tests are present
        }
    }

     // New method to extract the list of microbio tests
     public List<String> getMicrobioExaminations() {
        if (this.microbioTests != null && !this.microbioTests.isEmpty()) {
            // Assuming molecTests is a comma-separated string
            return Arrays.asList(this.microbioTests.split("\\s*,\\s*"));
        } else {
            return List.of(); // Return an empty list if no tests are present
        }
    }

     // New method to extract the list of microbio tests
     public List<String> getChemExaminations() {
        if (this.chemTests != null && !this.chemTests.isEmpty()) {
            // Assuming molecTests is a comma-separated string
            return Arrays.asList(this.chemTests.split("\\s*,\\s*"));
        } else {
            return List.of(); // Return an empty list if no tests are present
        }
    }

    public String getMolbioPending() {
        return molbioPending;
    }

    public void setMolbioPending(String molbioPending) {
        this.molbioPending = molbioPending;
    }

    public String getChemPending() {
        return chemPending;
    }

    public void setChemPending(String chemPending) {
        this.chemPending = chemPending;
    }

    public String getMicrobioPending() {
        return microbioPending;
    }

    public void setMicrobioPending(String microbioPending) {
        this.microbioPending = microbioPending;
    }
    @Transient // Field not persisted in the database
    private Map<String, String> testResultsMap = new HashMap<>();

    public Map<String, String> getTestResultsMap() {
        return testResultsMap;
    }

    public void setTestResultsMap(Map<String, String> testResultsMap) {
        this.testResultsMap = testResultsMap;
    }

}
