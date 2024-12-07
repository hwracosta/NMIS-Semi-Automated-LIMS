package com.example.semiautomatedlims.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.semiautomatedlims.Entity.ClientReqForm;

public interface ReleaseRepository extends JpaRepository<ClientReqForm, Long> {

    @Query("SELECT r FROM ClientReqForm r WHERE r.status = :status")
    List<ClientReqForm> findByStatus(@Param("status") String status);

    @Query("SELECT r FROM ClientReqForm r WHERE r.status IN :statuses")
    List<ClientReqForm> findByStatusIn(@Param("statuses") List<String> statuses);

    @Query("SELECT COUNT(r) FROM ClientReqForm r WHERE YEAR(r.submitDate) = :year AND MONTH(r.submitDate) = :month AND r.status = :status")
    int countByYearAndMonthAndStatus(@Param("year") int year, @Param("month") int month, @Param("status") String status);
    
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(r.ldControlNumber, LENGTH(r.ldControlNumber) - 3, 4) AS INTEGER)), 0) " +
       "FROM ClientReqForm r " +
       "WHERE EXTRACT(YEAR FROM r.submitDate) = :year " +
       "AND EXTRACT(MONTH FROM r.submitDate) = :month")
    Optional<Integer> findGlobalMaxSeriesForYearMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT r FROM ClientReqForm r WHERE r.status IN :statuses AND r.molbioPending = :pendingStatus")
    List<ClientReqForm> findByStatusInAndMolbioPending(@Param("statuses") List<String> statuses, @Param("pendingStatus") String pendingStatus);
}
