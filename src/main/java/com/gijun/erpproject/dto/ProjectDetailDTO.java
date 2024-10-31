package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.*;
import com.gijun.erpproject.Enum.ProjectStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// Project DTOs
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer requiredMembers;
    private BigDecimal contractAmount;
    private ProjectStatus status;
    private List<ProjectMemberDTO> members;
    private List<ProjectTechnologyDTO> technologies;
    private List<MilestoneResponseDTO> milestones;
    private double progress;

    public static ProjectDetailDTO from(Project project) {
        return ProjectDetailDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .requiredMembers(project.getRequiredMembers())
                .contractAmount(project.getContractAmount())
                .status(project.getStatus())
                .members(project.getProjectMembers().stream()
                        .map(ProjectMemberDTO::from)
                        .collect(Collectors.toList()))
                .technologies(project.getTechnologies().stream()
                        .map(ProjectTechnologyDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}