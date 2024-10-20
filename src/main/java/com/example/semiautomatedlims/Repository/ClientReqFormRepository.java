package com.example.semiautomatedlims.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.semiautomatedlims.Entity.Client;
import com.example.semiautomatedlims.Entity.ClientReqForm;

public interface ClientReqFormRepository extends JpaRepository<ClientReqForm, Long> {

    // Find requests by Client object
    @Query("SELECT r FROM ClientReqForm r WHERE r.client = :client")
    List<ClientReqForm> findByClient(@Param("client") Client client);

    // Find requests for Chem that are in "For Testing" status and have chem_pending as 'pending'
    List<ClientReqForm> findByStatusAndChemPending(String status, String chemPending);

    // Find requests for MolBio that are in "For Testing" status and have molbio_pending as 'pending'
    List<ClientReqForm> findByStatusAndMolbioPending(String status, String molbioPending);

    // Find requests for MicroBio that are in "For Testing" status and have microbio_pending as 'pending'
    List<ClientReqForm> findByStatusAndMicrobioPending(String status, String microbioPending);

    // Find requests where MolBio is transferred
    List<ClientReqForm> findByIsMolBioTransferredTrue();

    // Find requests where MicroBio is transferred
    List<ClientReqForm> findByIsMicroBioTransferredTrue();

    // Find requests where Chem is transferred
    List<ClientReqForm> findByIsChemTransferredTrue();

    // Existing methods to get requests with specific tests that are not transferred yet
    List<ClientReqForm> findByStatusAndIsMolBioTransferredFalseAndMolecTestsIsNotNull(String status);
    List<ClientReqForm> findByStatusAndIsMicroBioTransferredFalseAndMicrobioTestsIsNotNull(String status);
    List<ClientReqForm> findByStatusAndIsChemTransferredFalseAndChemTestsIsNotNull(String status);
}
