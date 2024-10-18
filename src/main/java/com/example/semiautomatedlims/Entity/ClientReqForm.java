package com.example.semiautomatedlims.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private int weightGrams;

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

    @Column(name = "transferred", nullable = true)
    private Boolean transferred = false;

    @Column(name = "ld_control_number", nullable = true)
    private String ldControlNumber;

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

    public Boolean getTransferred() {
        return transferred;
    }

    public void setTransferred(Boolean transferred) {
        this.transferred = transferred;
    }

    // New method to extract the list of molecular tests
    public List<String> getExaminations() {
        if (this.molecTests != null && !this.molecTests.isEmpty()) {
            // Assuming molecTests is a comma-separated string
            return Arrays.asList(this.molecTests.split("\\s*,\\s*"));
        } else {
            return List.of(); // Return an empty list if no tests are present
        }
    }
}
