package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.Project;
import com.gijun.erpproject.Enum.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.status = :status")
    List<Project> findAllByStatus(@Param("status") ProjectStatus status);

    @Query("SELECT p FROM Project p WHERE p.endDate < :now AND p.status = 'IN_PROGRESS'")
    List<Project> findDelayedProjects(@Param("now") LocalDate now);

    Page<Project> findByNameContaining(String name, Pageable pageable);

    // ProjectRepository.java
    @Query("SELECT p FROM Project p " +
            "JOIN FETCH p.projectMembers pm " +
            "JOIN FETCH pm.member " +
            "LEFT JOIN FETCH p.technologies " +
            "WHERE p.id = :projectId")
    Optional<Project> findByIdWithMembersAndTechnologies(@Param("projectId") Long projectId);
}
