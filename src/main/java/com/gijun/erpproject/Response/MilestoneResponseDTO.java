package com.gijun.erpproject.Response;

import com.gijun.erpproject.Entity.ProjectMilestone;
import com.gijun.erpproject.Enum.MilestoneStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private MilestoneStatus status;
    private Integer orderIndex;
    private double progress;
    private List<TaskResponseDTO> tasks;

    public static MilestoneResponseDTO from(ProjectMilestone milestone) {
        return MilestoneResponseDTO.builder()
                .id(milestone.getId())
                .title(milestone.getTitle())
                .description(milestone.getDescription())
                .plannedStartDate(milestone.getPlannedStartDate())
                .plannedEndDate(milestone.getPlannedEndDate())
                .actualStartDate(milestone.getActualStartDate())
                .actualEndDate(milestone.getActualEndDate())
                .status(milestone.getStatus())
                .orderIndex(milestone.getOrderIndex())
                .tasks(milestone.getTasks().stream()
                        .map(TaskResponseDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
