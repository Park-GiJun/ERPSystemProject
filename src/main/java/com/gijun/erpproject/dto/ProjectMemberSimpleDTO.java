package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.ProjectMember;
import com.gijun.erpproject.Enum.ProjectRole;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberSimpleDTO {
    private Long memberId;
    private String memberName;
    private String memberUsername;
    private ProjectRole role;

    public static ProjectMemberSimpleDTO from(ProjectMember projectMember) {
        return ProjectMemberSimpleDTO.builder()
                .memberId(projectMember.getMember().getId())
                .memberName(projectMember.getMember().getName())
                .memberUsername(projectMember.getMember().getUsername())
                .role(projectMember.getRole())
                .build();
    }
}
