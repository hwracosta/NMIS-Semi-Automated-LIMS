package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import com.example.semiautomatedlims.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientReqFormRepository extends JpaRepository<ClientReqForm, Long> {

    // Find requests by Client object
    @Query("SELECT r FROM ClientReqForm r WHERE r.client = :client")
    List<ClientReqForm> findByClient(@Param("client") Client client);
}
