package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReqFormRepository extends JpaRepository<ClientReqForm, Long> {
}
