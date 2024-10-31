package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.ProjectTechnology;
import com.gijun.erpproject.Enum.TechnologyCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTechnologyDTO {
    private Long id;
    private TechnologyCategory category;
    private String technologyName;
    private String version;

    public static ProjectTechnologyDTO from(ProjectTechnology technology) {
        return ProjectTechnologyDTO.builder()
                .id(technology.getId())
                .category(technology.getCategory())
                .technologyName(technology.getTechnologyName())
                .version(technology.getVersion())
                .build();
    }
}
