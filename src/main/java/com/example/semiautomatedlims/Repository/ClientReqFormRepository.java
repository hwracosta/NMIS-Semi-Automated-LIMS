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
    
    List<ClientReqForm> findByStatusAndMolecTestsIsNotNull(String status);
}
