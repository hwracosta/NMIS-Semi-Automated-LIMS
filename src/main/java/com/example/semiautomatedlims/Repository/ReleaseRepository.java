package com.example.semiautomatedlims.Repository;

import com.example.semiautomatedlims.Entity.ClientReqForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReleaseRepository extends JpaRepository<ClientReqForm, Long> {

    @Query("SELECT r FROM ClientReqForm r WHERE r.status = :status")
    List<ClientReqForm> findByStatus(@Param("status") String status);

    @Query("SELECT r FROM ClientReqForm r WHERE r.status IN :statuses")
    List<ClientReqForm> findByStatusIn(@Param("statuses") List<String> statuses);
}
