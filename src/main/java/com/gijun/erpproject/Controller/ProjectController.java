package com.gijun.erpproject.Controller;

import com.gijun.erpproject.Entity.*;
import com.gijun.erpproject.Service.*;
import com.gijun.erpproject.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Project Management", description = "프로젝트 관리 API")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final ProjectMilestoneService milestoneService;
    private final ProjectTaskService taskService;
    private final ProjectAnalyticsService analyticsService;

    @Operation(summary = "프로젝트 생성")
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @Valid @RequestBody ProjectCreateDTO request) {
        Project project = request.toEntity();
        Project savedProject = projectService.createProject(project, request.getMemberIds());
        return ResponseEntity.ok(ProjectResponseDTO.from(savedProject));
    }

    @Operation(summary = "프로젝트 목록 조회")
    @GetMapping
    public ResponseEntity<Page<ProjectResponseDTO>> getProjects(Pageable pageable) {
        Page<Project> projects = projectService.getAllProjects(pageable);
        return ResponseEntity.ok(projects.map(ProjectResponseDTO::from));
    }

    @Operation(summary = "프로젝트 상세 조회")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailDTO> getProject(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(ProjectDetailDTO.from(project));
    }

    @Operation(summary = "프로젝트 상태 변경")
    @PatchMapping("/{projectId}/status")
    public ResponseEntity<Void> updateProjectStatus(
            @PathVariable Long projectId,
            @RequestBody ProjectStatusUpdateDTO request) {
        projectService.updateProjectStatus(projectId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "프로젝트 진행률 조회")
    @GetMapping("/{projectId}/progress")
    public ResponseEntity<ProjectProgressDTO> getProjectProgress(@PathVariable Long projectId) {
        double progress = projectService.calculateProjectProgress(projectId);
        return ResponseEntity.ok(new ProjectProgressDTO(projectId, progress));
    }
}
