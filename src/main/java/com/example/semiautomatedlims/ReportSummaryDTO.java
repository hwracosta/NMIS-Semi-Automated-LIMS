package com.example.semiautomatedlims;

public class ReportSummaryDTO {
    private String testName;
    private Integer totalTests;
    private Integer positiveResults;
    private Integer negativeResults;

    public ReportSummaryDTO(String testName, Integer totalTests, Integer positiveResults, Integer negativeResults) {
        this.testName = testName;
        this.totalTests = totalTests;
        this.positiveResults = positiveResults;
        this.negativeResults = negativeResults;
    }

    // Getters and Setters
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public Integer getTotalTests() { return totalTests; }
    public void setTotalTests(Integer totalTests) { this.totalTests = totalTests; }

    public Integer getPositiveResults() { return positiveResults; }
    public void setPositiveResults(Integer positiveResults) { this.positiveResults = positiveResults; }

    public Integer getNegativeResults() { return negativeResults; }
    public void setNegativeResults(Integer negativeResults) { this.negativeResults = negativeResults; }
}

