package com.example.semiautomatedlims.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.semiautomatedlims.Entity.MicroBioData;

public interface MicroBioDataRepository extends JpaRepository<MicroBioData, Long> {

    @Query("SELECT m FROM MicroBioData m WHERE m.ldControlNumber = :ldControlNumber")
    List<MicroBioData> findByLdControlNumber(@Param("ldControlNumber") String ldControlNumber);

// Get total tests performed (assuming 'result' column contains the result values)
    @Query("SELECT COUNT(m) FROM MicroBioData m")
    Integer countTotalTests();

    // Get positive results
    @Query("SELECT COUNT(m) FROM MicroBioData m WHERE m.micRemarks = 'Positive'")
    Integer countPositiveResults();

    // Get negative results
    @Query("SELECT COUNT(m) FROM MicroBioData m WHERE m.micRemarks = 'Negative'")
    Integer countNegativeResults();

    // Find records by analyte
    List<MicroBioData> findByMicTestName(String micTestName);

    // Add a query method to fetch data based on month and year
    @Query("SELECT m FROM MicroBioData m WHERE MONTH(m.analysisDate) = :month AND YEAR(m.analysisDate) = :year")
    List<MicroBioData> findByMonthAndYear(int month, int year);


}
