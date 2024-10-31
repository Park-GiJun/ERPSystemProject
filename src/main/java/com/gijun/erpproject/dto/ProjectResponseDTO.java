package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.Project;
import com.gijun.erpproject.Enum.ProjectStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer requiredMembers;
    private BigDecimal contractAmount;
    private ProjectStatus status;
    private double progress;

    public static ProjectResponseDTO from(Project project) {
        return ProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .requiredMembers(project.getRequiredMembers())
                .contractAmount(project.getContractAmount())
                .status(project.getStatus())
                .build();
    }
}