package com.gijun.erpproject.Controller;

import com.gijun.erpproject.Entity.Project;
import com.gijun.erpproject.Enum.ProjectStatus;
import com.gijun.erpproject.Enum.TaskStatus;
import com.gijun.erpproject.Service.ProjectAnalyticsService;
import com.gijun.erpproject.dto.ProjectResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Project Analytics", description = "프로젝트 분석 API")
@RestController
@RequestMapping("/api/projects/analytics")
@RequiredArgsConstructor
public class ProjectAnalyticsController {

    private final ProjectAnalyticsService analyticsService;

    @Operation(summary = "프로젝트 현황 분석")
    @GetMapping("/status")
    public ResponseEntity<Map<ProjectStatus, Long>> getProjectStatusAnalytics() {
        return ResponseEntity.ok(analyticsService.analyzeProjectStatus());
    }

    @Operation(summary = "멤버별 작업 현황 분석")
    @GetMapping("/{projectId}/member-tasks")
    public ResponseEntity<Map<Long, Map<TaskStatus, Long>>> getMemberTaskAnalytics(
            @PathVariable Long projectId) {
        return ResponseEntity.ok(analyticsService.analyzeMemberTaskStatus(projectId));
    }

    @Operation(summary = "지연 프로젝트 조회")
    @GetMapping("/delayed")
    public ResponseEntity<List<ProjectResponseDTO>> getDelayedProjects() {
        List<Project> projects = analyticsService.analyzeDelayedProjects();
        return ResponseEntity.ok(projects.stream()
                .map(ProjectResponseDTO::from)
                .toList());
    }
}