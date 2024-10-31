package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.ProjectMember;
import com.gijun.erpproject.Enum.ProjectRole;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberDTO {
    private Long id;
    private Long projectId;
    private String projectName;
    private Long memberId;
    private String memberName;
    private String memberUsername;
    private ProjectRole role;
    private LocalDate joinDate;
    private LocalDate endDate;

    public static ProjectMemberDTO from(ProjectMember projectMember) {
        return ProjectMemberDTO.builder()
                .id(projectMember.getId())
                .projectId(projectMember.getProject().getId())
                .projectName(projectMember.getProject().getName())
                .memberId(projectMember.getMember().getId())
                .memberName(projectMember.getMember().getName())
                .memberUsername(projectMember.getMember().getUsername())
                .role(projectMember.getRole())
                .joinDate(projectMember.getJoinDate())
                .endDate(projectMember.getEndDate())
                .build();
    }
}
