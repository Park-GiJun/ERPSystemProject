package com.gijun.erpproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectProgressDTO {
    private Long projectId;
    private double progress;

    public ProjectProgressDTO(Long projectId, double progress) {
        this.projectId = projectId;
        this.progress = progress;
    }
}
