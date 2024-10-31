package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.ProjectMilestone;
import com.gijun.erpproject.Enum.MilestoneStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectMilestoneRepository extends JpaRepository<ProjectMilestone, Long> {
    List<ProjectMilestone> findByProjectIdOrderByOrderIndex(Long projectId);
    List<ProjectMilestone> findByProjectIdAndStatus(Long projectId, MilestoneStatus status);

    @Query("SELECT m FROM ProjectMilestone m WHERE m.project.id = :projectId AND m.plannedEndDate < :now AND m.status != 'COMPLETED'")
    List<ProjectMilestone> findDelayedMilestones(@Param("projectId") Long projectId, @Param("now") LocalDate now);
}