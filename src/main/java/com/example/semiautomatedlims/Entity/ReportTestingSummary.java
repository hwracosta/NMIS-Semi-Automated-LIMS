package com.example.semiautomatedlims.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReportTestingSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testName;
    private int totalAnalysisPerformed;
    private int positiveResults;
    private int negativeResults;

    // Getters and Setters
}
