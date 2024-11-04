package com.example.semiautomatedlims.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.ChemData;

public interface ChemDataRepository extends JpaRepository<ChemData, Long> {

    @Query("SELECT m FROM ChemData m WHERE m.ldControlNumber = :ldControlNumber")
    List<ChemData> findByLdControlNumber(@Param("ldControlNumber") String ldControlNumber);

    // Get total tests performed (assuming 'result' column contains the result values)
    @Query("SELECT COUNT(m) FROM ChemData m")
    Integer countTotalTests();

    // Get positive results
    @Query("SELECT COUNT(m) FROM ChemData m WHERE m.result = 'Positive'")
    Integer countPositiveResults();

    // Get negative results
    @Query("SELECT COUNT(m) FROM ChemData m WHERE m.result = 'Negative'")
    Integer countNegativeResults();
}
