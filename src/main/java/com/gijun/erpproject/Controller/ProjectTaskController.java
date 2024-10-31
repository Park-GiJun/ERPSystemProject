package com.gijun.erpproject.Controller;

import com.gijun.erpproject.Entity.ProjectTask;
import com.gijun.erpproject.Entity.TaskComment;
import com.gijun.erpproject.Service.ProjectTaskService;
import com.gijun.erpproject.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project Tasks", description = "프로젝트 작업 관리 API")
@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class ProjectTaskController {

    private final ProjectTaskService taskService;

    @Operation(summary = "작업 생성")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskCreateDTO request) {
        ProjectTask task = taskService.createTask(request.toEntity());
        return ResponseEntity.ok(TaskResponseDTO.from(task));
    }

    @Operation(summary = "작업 목록 조회")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks(
            @PathVariable Long projectId,
            @RequestParam(required = false) Long milestoneId) {
        List<ProjectTask> tasks = milestoneId != null ?
                taskService.getTasksByMilestone(milestoneId) :
                taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks.stream()
                .map(TaskResponseDTO::from)
                .toList());
    }

    @Operation(summary = "작업 진행률 업데이트")
    @PatchMapping("/{taskId}/progress")
    public ResponseEntity<Void> updateTaskProgress(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskProgressUpdateDTO request) {
        taskService.updateTaskProgress(taskId, request.getProgress());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작업 상태 변경")
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskStatusUpdateDTO request) {
        taskService.updateTaskStatus(taskId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작업 코멘트 추가")
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody CommentCreateDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {
        TaskComment comment = taskService.addComment(taskId, request.getContent(), userDetails.getUsername());
        return ResponseEntity.ok(CommentResponseDTO.from(comment));
    }

    @Operation(summary = "작업 코멘트 목록 조회")
    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(
            @PathVariable Long projectId,
            @PathVariable Long taskId) {
        List<TaskComment> comments = taskService.getTaskComments(taskId);
        return ResponseEntity.ok(comments.stream()
                .map(CommentResponseDTO::from)
                .toList());
    }
}