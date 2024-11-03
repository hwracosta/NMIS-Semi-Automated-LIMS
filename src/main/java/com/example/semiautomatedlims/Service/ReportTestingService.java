package com.example.semiautomatedlims.Service;

import com.example.semiautomatedlims.Entity.ReportTestingSummary;
import com.example.semiautomatedlims.Repository.ReportTestingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportTestingService {

    @Autowired
    private ReportTestingRepository reportTestingRepository;

    public List<ReportTestingSummary> getAllTestSummaries() {
        return reportTestingRepository.findAll();
    }
}
