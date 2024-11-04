package com.example.semiautomatedlims.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report_testing_summary")
public class ReportTestingSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "category", nullable = false)
    private String category; // E.g., "Microbiological", "Molecular Biology", etc.

    @Column(name = "total_analysis_performed")
    private Integer totalAnalysisPerformed;

    @Column(name = "positive_results")
    private Integer positiveResults;

    @Column(name = "negative_results")
    private Integer negativeResults;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalAnalysisPerformed() {
        return totalAnalysisPerformed;
    }

    public void setTotalAnalysisPerformed(Integer totalAnalysisPerformed) {
        this.totalAnalysisPerformed = totalAnalysisPerformed;
    }

    public Integer getPositiveResults() {
        return positiveResults;
    }

    public void setPositiveResults(Integer positiveResults) {
        this.positiveResults = positiveResults;
    }

    public Integer getNegativeResults() {
        return negativeResults;
    }

    public void setNegativeResults(Integer negativeResults) {
        this.negativeResults = negativeResults;
    }
}
