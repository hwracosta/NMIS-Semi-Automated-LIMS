package com.example.semiautomatedlims;

import java.util.HashMap;
import java.util.Map;

public class ReportSummaryDTO {
    private String testName;
    private Integer totalTests;
    private Integer positiveResults;
    private Integer negativeResults;

    // A map to hold positive/negative results for each month (1 to 12)
    private Map<Integer, Integer> monthlyPositiveResults;
    private Map<Integer, Integer> monthlyNegativeResults;

    public ReportSummaryDTO(String testName, Integer totalTests, Integer positiveResults, Integer negativeResults) {
        this.testName = testName;
        this.totalTests = totalTests;
        this.positiveResults = positiveResults;
        this.negativeResults = negativeResults;

        // Initialize the maps for each month with 0 values
        this.monthlyPositiveResults = new HashMap<>();
        this.monthlyNegativeResults = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlyPositiveResults.put(i, 0);
            monthlyNegativeResults.put(i, 0);
        }
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

    // Methods to update positive and negative results for a specific month
    public void incrementPositiveResultsForMonth(int month) {
        this.monthlyPositiveResults.put(month, this.monthlyPositiveResults.get(month) + 1);
        this.positiveResults++;
    }

    public void incrementNegativeResultsForMonth(int month) {
        this.monthlyNegativeResults.put(month, this.monthlyNegativeResults.get(month) + 1);
        this.negativeResults++;
    }

    // Getters for monthly results
    public Integer getMonthlyPositiveResults(int month) {
        return this.monthlyPositiveResults.get(month);
    }

    public Integer getMonthlyNegativeResults(int month) {
        return this.monthlyNegativeResults.get(month);
    }
}

