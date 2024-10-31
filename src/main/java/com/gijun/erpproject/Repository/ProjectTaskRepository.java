package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.ProjectTask;
import com.gijun.erpproject.Enum.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
    List<ProjectTask> findByMilestoneId(Long milestoneId);
    List<ProjectTask> findByAssigneeId(Long assigneeId);
    List<ProjectTask> findByMilestoneIdAndStatus(Long milestoneId, TaskStatus status);

    @Query("SELECT t FROM ProjectTask t WHERE t.milestone.project.id = :projectId AND t.status != 'COMPLETED' ORDER BY t.priority DESC")
    List<ProjectTask> findActiveTasksByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT t FROM ProjectTask t WHERE t.milestone.project.id = :projectId")
    List<ProjectTask> findByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT t FROM ProjectTask t WHERE t.milestone.project.id = :projectId " +
            "AND t.status = :status")
    List<ProjectTask> findByProjectIdAndStatus(
            @Param("projectId") Long projectId,
            @Param("status") TaskStatus status);

    @Query("SELECT t FROM ProjectTask t WHERE t.milestone.project.id = :projectId " +
            "ORDER BY t.priority DESC, t.plannedEndDate ASC")
    List<ProjectTask> findByProjectIdOrderByPriorityAndEndDate(
            @Param("projectId") Long projectId);
}
