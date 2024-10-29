package com.gijun.erpproject.Login.Repository;

import com.gijun.erpproject.Entity.Login.LoginHistory;
import com.gijun.erpproject.Entity.Login.LoginStatus;
import com.gijun.erpproject.Entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    Page<LoginHistory> findByMember(Member member, Pageable pageable);

    @Query("SELECT lh FROM LoginHistory lh WHERE " +
            "(:username IS NULL OR lh.member.username = :username) AND " +
            "(:status IS NULL OR lh.status = :status) AND " +
            "(:startDate IS NULL OR lh.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR lh.createdAt <= :endDate)")
    Page<LoginHistory> findBySearchCriteria(
            @Param("username") String username,
            @Param("status") LoginStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
