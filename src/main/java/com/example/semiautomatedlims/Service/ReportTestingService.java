package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.Entity.MicroBioData;
import com.example.semiautomatedlims.Entity.MolBioData;
import com.example.semiautomatedlims.ReportSummaryDTO;
import com.example.semiautomatedlims.Repository.ChemDataRepository;
import com.example.semiautomatedlims.Repository.MicroBioDataRepository;
import com.example.semiautomatedlims.Repository.MolBioDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportTestingService {

    private final ChemDataRepository chemDataRepository;
    private final MicroBioDataRepository microBioDataRepository;
    private final MolBioDataRepository molBioDataRepository;

    @Autowired
    public ReportTestingService(ChemDataRepository chemDataRepository, 
                                MicroBioDataRepository microBioDataRepository, 
                                MolBioDataRepository molBioDataRepository) {
        this.chemDataRepository = chemDataRepository;
        this.microBioDataRepository = microBioDataRepository;
        this.molBioDataRepository = molBioDataRepository;
    }

    // Existing method for chemical tests
    public List<ReportSummaryDTO> getChemicalTestSummaries() {
        List<ReportSummaryDTO> summaries = new ArrayList<>();

        chemDataRepository.findAll().stream()
            .collect(Collectors.groupingBy(ChemData::getAnalyte))
            .forEach((analyte, records) -> {
                long total = records.size();
                long positive = records.stream().filter(r -> "Positive".equalsIgnoreCase(r.getRemarks())).count();
                long negative = records.stream().filter(r -> "Negative".equalsIgnoreCase(r.getRemarks())).count();

                summaries.add(new ReportSummaryDTO(analyte, (int) total, (int) positive, (int) negative));
            });

        return summaries;
    }

    // Method for microbiological tests
    public List<ReportSummaryDTO> getMicrobiologicalTests() {
        List<ReportSummaryDTO> summaries = new ArrayList<>();

        microBioDataRepository.findAll().stream()
            .collect(Collectors.groupingBy(MicroBioData::getMicTestName))  // Group by test name
            .forEach((testName, records) -> {
                long total = records.size();
                long positive = records.stream().filter(r -> "Positive".equalsIgnoreCase(r.getMicRemarks())).count();
                long negative = records.stream().filter(r -> "Negative".equalsIgnoreCase(r.getMicRemarks())).count();

                summaries.add(new ReportSummaryDTO(testName, (int) total, (int) positive, (int) negative));
            });

        return summaries;
    }

    // Method for molecular biology tests
    public List<ReportSummaryDTO> getMolecularBiologyTests() {
        List<ReportSummaryDTO> summaries = new ArrayList<>();

        molBioDataRepository.findAll().stream()
            .collect(Collectors.groupingBy(MolBioData::getTestName))  // Group by test name
            .forEach((testName, records) -> {
                long total = records.size();
                long positive = records.stream().filter(r -> "Positive".equalsIgnoreCase(r.getRemarks())).count();
                long negative = records.stream().filter(r -> "Negative".equalsIgnoreCase(r.getRemarks())).count();

                summaries.add(new ReportSummaryDTO(testName, (int) total, (int) positive, (int) negative));
            });

        return summaries;
    }
}
