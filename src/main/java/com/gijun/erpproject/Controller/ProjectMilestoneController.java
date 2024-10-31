package com.gijun.erpproject.Controller;

import com.gijun.erpproject.Entity.ProjectMilestone;
import com.gijun.erpproject.Service.ProjectMilestoneService;
import com.gijun.erpproject.Service.ProjectService;
import com.gijun.erpproject.dto.MilestoneCreateDTO;
import com.gijun.erpproject.dto.MilestoneProgressDTO;
import com.gijun.erpproject.dto.MilestoneResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project Milestones", description = "프로젝트 마일스톤 관리 API")
@RestController
@RequestMapping("/api/projects/{projectId}/milestones")
@RequiredArgsConstructor
public class ProjectMilestoneController {

    private final ProjectMilestoneService milestoneService;
    private final ProjectService projectService;

    @Operation(summary = "마일스톤 생성")
    @PostMapping
    public ResponseEntity<MilestoneResponseDTO> createMilestone(
            @PathVariable Long projectId,
            @Valid @RequestBody MilestoneCreateDTO request) {
        ProjectMilestone milestone = milestoneService.createMilestone(
                request.toEntity(projectService.getProjectById(projectId)));
        return ResponseEntity.ok(MilestoneResponseDTO.from(milestone));
    }

    @Operation(summary = "마일스톤 목록 조회")
    @GetMapping
    public ResponseEntity<List<MilestoneResponseDTO>> getMilestones(
            @PathVariable Long projectId) {
        List<ProjectMilestone> milestones = milestoneService.getMilestonesByProject(projectId);
        return ResponseEntity.ok(milestones.stream()
                .map(MilestoneResponseDTO::from)
                .toList());
    }

    @Operation(summary = "마일스톤 진행률 조회")
    @GetMapping("/{milestoneId}/progress")
    public ResponseEntity<MilestoneProgressDTO> getMilestoneProgress(
            @PathVariable Long projectId,
            @PathVariable Long milestoneId) {
        double progress = milestoneService.calculateMilestoneProgress(milestoneId);
        return ResponseEntity.ok(new MilestoneProgressDTO(milestoneId, progress));
    }
}