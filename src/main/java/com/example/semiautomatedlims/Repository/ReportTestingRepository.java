package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.ReportTestingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTestingRepository extends JpaRepository<ReportTestingSummary, Long> {
}
