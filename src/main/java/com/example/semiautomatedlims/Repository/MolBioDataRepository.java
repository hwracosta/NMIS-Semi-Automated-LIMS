package com.example.semiautomatedlims.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.MolBioData;

public interface MolBioDataRepository extends JpaRepository<MolBioData, Long> {

    @Query("SELECT m FROM MolBioData m WHERE m.clientReqForm.ldControlNumber = :ldControlNumber")
    List<MolBioData> findByClientReqFormLdControlNumber(@Param("ldControlNumber") String ldControlNumber);
}
