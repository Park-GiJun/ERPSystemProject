package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT m.enabled FROM Member m WHERE m.username = :username")
    Optional<Boolean> findEnabledByUsername(@Param("username") String username);

}
