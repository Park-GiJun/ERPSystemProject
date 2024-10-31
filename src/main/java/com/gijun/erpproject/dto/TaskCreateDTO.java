package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.ProjectTask;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateDTO {
    @NotBlank(message = "작업 제목은 필수입니다")
    private String title;

    private String description;

    @NotNull(message = "마일스톤 ID는 필수입니다")
    private Long milestoneId;

    @NotNull(message = "담당자 ID는 필수입니다")
    private Long assigneeId;

    @NotNull(message = "계획 시작일은 필수입니다")
    private LocalDate plannedStartDate;

    @NotNull(message = "계획 종료일은 필수입니다")
    private LocalDate plannedEndDate;

    @Min(value = 1, message = "우선순위는 1 이상이어야 합니다")
    @Max(value = 5, message = "우선순위는 5 이하여야 합니다")
    private Integer priority;

    public ProjectTask toEntity() {
        return ProjectTask.builder()
                .title(title)
                .description(description)
                .plannedStartDate(plannedStartDate)
                .plannedEndDate(plannedEndDate)
                .priority(priority)
                .build();
    }
}
