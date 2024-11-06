package com.gijun.erpproject.Response;

import com.gijun.erpproject.Entity.Team;
import com.gijun.erpproject.Entity.TeamOrganization;
import com.gijun.erpproject.dto.TeamMemberDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record TeamResponseDTO(
        Long id,
        String teamName,
        String description,
        Integer teamLevel,
        Long parentTeamId,
        String parentTeamName,
        List<TeamResponseDTO> children,
        List<TeamMemberDTO> members
) {
    public static TeamResponseDTO from(Team team, List<TeamOrganization> members) {
        return new TeamResponseDTO(
                team.getId(),
                team.getTeamName(),
                team.getDescription(),
                team.getTeamLevel(),
                team.getParentTeam() != null ? team.getParentTeam().getId() : null,
                team.getParentTeam() != null ? team.getParentTeam().getTeamName() : null,
                new ArrayList<>(),
                members.stream()
                        .filter(m -> m.getTeam().getId().equals(team.getId()))
                        .map(TeamMemberDTO::from)
                        .collect(Collectors.toList())
        );
    }
}
