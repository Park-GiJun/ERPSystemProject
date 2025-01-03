package com.gijun.erpproject.dto;

import com.gijun.erpproject.Enum.ProjectRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberRoleUpdateDTO {
    @NotNull(message = "역할은 필수입니다")
    private ProjectRole role;
}
