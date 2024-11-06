package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Entity.TeamOrganization;

public record TeamMemberDTO(
        Long memberId,
        String name,
        String email,
        String position,
        String responsibility,
        boolean isTeamLeader
) {
    public static TeamMemberDTO from(TeamOrganization organization) {
        Member member = organization.getMember();
        return new TeamMemberDTO(
                member.getId(),
                member.getName(),
                member.getEmail(),
                organization.getPosition(),
                organization.getResponsibility(),
                organization.isTeamLeader()
        );
    }
}
