package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.ProjectTask;
import com.gijun.erpproject.Enum.TaskStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private TaskStatus status;
    private Integer progress;
    private Integer priority;
    private MemberSimpleDTO assignee;
    private List<CommentResponseDTO> comments;

    public static TaskResponseDTO from(ProjectTask task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .plannedStartDate(task.getPlannedStartDate())
                .plannedEndDate(task.getPlannedEndDate())
                .actualStartDate(task.getActualStartDate())
                .actualEndDate(task.getActualEndDate())
                .status(task.getStatus())
                .progress(task.getProgress())
                .priority(task.getPriority())
                .assignee(MemberSimpleDTO.from(task.getAssignee()))
                .comments(task.getComments().stream()
                        .map(CommentResponseDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}

