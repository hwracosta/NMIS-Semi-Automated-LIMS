package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.ReportTestingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportTestingRepository extends JpaRepository<ReportTestingSummary, Long> {

    // Retrieve all microbiological tests
    @Query("SELECT r FROM ReportTestingSummary r WHERE r.category = 'Microbiological'")
    List<ReportTestingSummary> findAllMicrobiologicalTests();

    // Retrieve all molecular biology tests
    @Query("SELECT r FROM ReportTestingSummary r WHERE r.category = 'Molecular Biology'")
    List<ReportTestingSummary> findAllMolecularBiologyTests();

    // Retrieve all chemical/veterinary drug residue tests
    @Query("SELECT r FROM ReportTestingSummary r WHERE r.category = 'Chemical/Veterinary Drug Residue'")
    List<ReportTestingSummary> findAllChemicalTests();
}
