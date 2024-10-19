package com.example.semiautomatedlims.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MicroBioData;

public interface MicroBioDataRepository extends JpaRepository<MicroBioData, Long> {

    @Query("SELECT m FROM MicroBioData m WHERE m.ldControlNumber = :ldControlNumber")
    List<MicroBioData> findByLdControlNumber(@Param("ldControlNumber") String ldControlNumber);
}
