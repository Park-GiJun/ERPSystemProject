package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.ProjectMember;
import com.gijun.erpproject.Enum.ProjectRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByProjectId(Long projectId);
    List<ProjectMember> findByMemberId(Long memberId);
    Optional<ProjectMember> findByProjectIdAndMemberId(Long projectId, Long memberId);

    @Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.role = :role")
    List<ProjectMember> findByProjectAndRole(@Param("projectId") Long projectId, @Param("role") ProjectRole role);

    @EntityGraph(attributePaths = {"member", "project"})
    Optional<ProjectMember> findById(Long id);

}
