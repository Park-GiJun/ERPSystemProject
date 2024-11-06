package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findByParentTeamIsNull(Pageable pageable);
}
