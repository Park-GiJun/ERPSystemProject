package com.gijun.erpproject.Login.Repository;

import com.gijun.erpproject.Entity.Login.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    // username과 IP 주소로 로그인 시도 기록 찾기
    Optional<LoginAttempt> findByUsernameAndIpAddress(String username, String ipAddress);

    // 현재 차단된 모든 로그인 시도 찾기
    @Query("SELECT la FROM LoginAttempt la WHERE la.blockedUntil > :now")
    List<LoginAttempt> findAllCurrentlyBlocked(@Param("now") LocalDateTime now);

    // 특정 시간 이전의 로그인 시도 기록 삭제 (정리용)
    void deleteByLastAttemptBefore(LocalDateTime time);

    // 특정 시간 이전의 시도 횟수 초기화
    @Modifying
    @Query("UPDATE LoginAttempt la SET la.attempts = 0, la.blockedUntil = NULL WHERE la.lastAttempt < :time")
    void resetOldAttempts(@Param("time") LocalDateTime time);

    // 특정 사용자의 최근 로그인 시도 횟수 조회
    @Query("SELECT COUNT(la) FROM LoginAttempt la WHERE la.username = :username AND la.lastAttempt > :since")
    int countRecentAttempts(@Param("username") String username, @Param("since") LocalDateTime since);

    // 의심스러운 IP 주소 찾기 (많은 실패 시도가 있는 IP)
    @Query("SELECT la.ipAddress, COUNT(la) as attempts FROM LoginAttempt la " +
            "WHERE la.lastAttempt > :since GROUP BY la.ipAddress HAVING COUNT(la) >= :threshold")
    List<Object[]> findSuspiciousIpAddresses(@Param("since") LocalDateTime since, @Param("threshold") int threshold);
}