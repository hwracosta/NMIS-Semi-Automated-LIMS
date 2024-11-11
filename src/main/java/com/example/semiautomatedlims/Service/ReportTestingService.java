package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ChemData;
import com.example.semiautomatedlims.ReportSummaryDTO;
import com.example.semiautomatedlims.Repository.ChemDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReportTestingService {

    private final ChemDataRepository chemDataRepository;

    @Autowired
    public ReportTestingService(ChemDataRepository chemDataRepository) {
        this.chemDataRepository = chemDataRepository;
    }

    public List<ReportSummaryDTO> getChemicalTestSummaries() {
        // Retrieve all records and group them by analyte in-memory
        List<ReportSummaryDTO> summaries = new ArrayList<>();
        
        chemDataRepository.findAll().stream()
            .collect(Collectors.groupingBy(ChemData::getAnalyte))
            .forEach((analyte, records) -> {
                long total = records.size();
                long positive = records.stream().filter(r -> "Positive".equalsIgnoreCase(r.getResult())).count();
                long negative = records.stream().filter(r -> "Negative".equalsIgnoreCase(r.getResult())).count();
                
                summaries.add(new ReportSummaryDTO(analyte, (int) total, (int) positive, (int) negative));
            });

        return summaries;
    }
}
