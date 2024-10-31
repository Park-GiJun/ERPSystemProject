package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.Project;
import com.gijun.erpproject.Entity.ProjectMilestone;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneCreateDTO {
    @NotBlank(message = "마일스톤 제목은 필수입니다")
    private String title;

    private String description;

    @NotNull(message = "계획 시작일은 필수입니다")
    private LocalDate plannedStartDate;

    @NotNull(message = "계획 종료일은 필수입니다")
    private LocalDate plannedEndDate;

    @Min(value = 0, message = "순서는 0 이상이어야 합니다")
    private Integer orderIndex;

    public ProjectMilestone toEntity(Project project) {
        return ProjectMilestone.builder()
                .project(project)
                .title(title)
                .description(description)
                .plannedStartDate(plannedStartDate)
                .plannedEndDate(plannedEndDate)
                .orderIndex(orderIndex)
                .build();
    }
}
