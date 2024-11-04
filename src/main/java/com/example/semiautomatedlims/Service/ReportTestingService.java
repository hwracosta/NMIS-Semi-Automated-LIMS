package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ReportTestingSummary;
import com.example.semiautomatedlims.Repository.ReportTestingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReportTestingService {
    private static final Logger logger = LoggerFactory.getLogger(ReportTestingService.class);

    @Autowired
    private ReportTestingRepository reportTestingRepository;

    public List<ReportTestingSummary> getMicrobiologicalTests() {
        List<ReportTestingSummary> microbiologicalTests = reportTestingRepository.findAllMicrobiologicalTests();
        logger.info("Microbiological Tests: " + microbiologicalTests.size() + " records retrieved.");
        return microbiologicalTests;
    }

    public List<ReportTestingSummary> getMolecularBiologyTests() {
        List<ReportTestingSummary> molecularBiologyTests = reportTestingRepository.findAllMolecularBiologyTests();
        logger.info("Molecular Biology Tests: " + molecularBiologyTests.size() + " records retrieved.");
        return molecularBiologyTests;
    }

    public List<ReportTestingSummary> getChemicalTests() {
        List<ReportTestingSummary> chemicalTests = reportTestingRepository.findAllChemicalTests();
        logger.info("Chemical Tests: " + chemicalTests.size() + " records retrieved.");
        return chemicalTests;
    }
}
