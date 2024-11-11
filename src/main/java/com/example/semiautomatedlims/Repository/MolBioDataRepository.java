package com.example.semiautomatedlims.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.semiautomatedlims.Entity.MolBioData;

public interface MolBioDataRepository extends JpaRepository<MolBioData, Long> {

    @Query("SELECT m FROM MolBioData m WHERE m.ldControlNumber = :ldControlNumber")
    List<MolBioData> findByLdControlNumber(@Param("ldControlNumber") String ldControlNumber);

    // Get total tests performed (assuming 'result' column contains the result values)
    @Query("SELECT COUNT(m) FROM MolBioData m")
    Integer countTotalTests();

    // Get positive results
    @Query("SELECT COUNT(m) FROM MolBioData m WHERE m.remarks = 'Positive'")
    Integer countPositiveResults();

    // Get negative results
    @Query("SELECT COUNT(m) FROM MolBioData m WHERE m.remarks = 'Negative'")
    Integer countNegativeResults();

    // Find records by analyte
    List<MolBioData> findByTestName(String testName);
}
