package com.gijun.erpproject.dto;

import com.gijun.erpproject.Enum.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusUpdateDTO {
    @NotNull(message = "상태는 필수입니다")
    private ProjectStatus status;
}