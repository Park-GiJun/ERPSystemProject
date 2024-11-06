package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.Team;
import com.gijun.erpproject.Entity.TeamOrganization;
import com.gijun.erpproject.Repository.TeamOrganizationRepository;
import com.gijun.erpproject.Repository.TeamRepository;
import com.gijun.erpproject.Response.PageResponseDTO;
import com.gijun.erpproject.Response.TeamResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamOrganizationRepository teamOrganizationRepository;

    public PageResponseDTO<TeamResponseDTO> getAllTeamsHierarchy(int page, int size) {
        // 페이지네이션으로 최상위 팀만 조회
        Pageable pageable = PageRequest.of(page, size);
        Page<Team> rootTeams = teamRepository.findByParentTeamIsNull(pageable);

        // 전체 팀과 멤버 조회 (하위 팀 구성을 위해)
        List<Team> allTeams = teamRepository.findAll();
        List<TeamOrganization> allMembers = teamOrganizationRepository.findAll();

        // TeamResponseDTO 맵 생성
        Map<Long, TeamResponseDTO> teamDTOMap = allTeams.stream()
                .collect(Collectors.toMap(
                        Team::getId,
                        team -> TeamResponseDTO.from(team, allMembers)
                ));

        // 하위 팀 구조 구성
        allTeams.forEach(team -> {
            if (team.getParentTeam() != null) {
                TeamResponseDTO parentDTO = teamDTOMap.get(team.getParentTeam().getId());
                TeamResponseDTO childDTO = teamDTOMap.get(team.getId());
                parentDTO.children().add(childDTO);
            }
        });

        // 페이지의 루트 팀들에 대해서만 응답 구성
        List<TeamResponseDTO> pageContent = rootTeams.getContent().stream()
                .map(team -> teamDTOMap.get(team.getId()))
                .collect(Collectors.toList());

        return PageResponseDTO.from(new PageImpl<>(
                pageContent,
                pageable,
                rootTeams.getTotalElements()
        ));
    }
}