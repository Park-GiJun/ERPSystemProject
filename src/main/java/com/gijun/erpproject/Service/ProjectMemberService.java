package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.*;
import com.gijun.erpproject.Enum.ProjectRole;
import com.gijun.erpproject.Exception.DuplicateMemberException;
import com.gijun.erpproject.Exception.ProjectNotFoundException;
import com.gijun.erpproject.Repository.ProjectMemberRepository;
import com.gijun.erpproject.Repository.ProjectRepository;
import com.gijun.erpproject.dto.ProjectMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final MemberService memberService;

    /**
     * 프로젝트에 멤버 추가
     */
    @Transactional
    public ProjectMember addMemberToProject(Long projectId, Long memberId, ProjectRole role) {
        // 프로젝트 존재 확인
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + projectId));

        // 멤버 존재 확인
        Member member = memberService.getMemberById(memberId);

        // 이미 프로젝트에 속해있는지 확인
        if (projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId).isPresent()) {
            throw new DuplicateMemberException("Member is already in the project");
        }

        // 프로젝트 멤버 생성 및 저장
        ProjectMember projectMember = ProjectMember.builder()
                .project(project)
                .member(member)
                .role(role)
                .build();

        return projectMemberRepository.save(projectMember);
    }

    /**
     * 프로젝트 멤버 제거
     */
    @Transactional
    public void removeMemberFromProject(Long projectId, Long memberId) {
        ProjectMember projectMember = projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member is not in the project"));

        projectMember.updateEndDate(LocalDate.now());
    }

    /**
     * 프로젝트 멤버 역할 변경
     */
    @Transactional
    public void updateMemberRole(Long projectId, Long memberId, ProjectRole newRole) {
        ProjectMember projectMember = projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member is not in the project"));

        ProjectMember updatedMember = ProjectMember.builder()
                .project(projectMember.getProject())
                .member(projectMember.getMember())
                .role(newRole)
                .build();

        projectMemberRepository.save(updatedMember);
    }

    /**
     * 프로젝트의 모든 멤버 조회
     */
    public List<ProjectMember> getProjectMembers(Long projectId) {
        return projectMemberRepository.findByProjectId(projectId);
    }

    /**
     * 특정 역할을 가진 프로젝트 멤버 조회
     */
    public List<ProjectMember> getProjectMembersByRole(Long projectId, ProjectRole role) {
        return projectMemberRepository.findByProjectAndRole(projectId, role);
    }

    /**
     * 멤버가 참여중인 모든 프로젝트 조회
     */
    public List<ProjectMember> getMemberProjects(Long memberId) {
        return projectMemberRepository.findByMemberId(memberId);
    }

    /**
     * 프로젝트 멤버 통계 조회
     */
    public Map<ProjectRole, Long> getProjectMemberStats(Long projectId) {
        List<ProjectMember> members = getProjectMembers(projectId);
        return members.stream()
                .collect(Collectors.groupingBy(
                        ProjectMember::getRole,
                        Collectors.counting()
                ));
    }

    /**
     * 프로젝트의 특정 역할 담당자 확인
     */
    public boolean hasRole(Long projectId, Long memberId, ProjectRole role) {
        return projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .map(member -> member.getRole() == role)
                .orElse(false);
    }

    /**
     * 프로젝트 멤버 수 체크
     */
    public boolean isProjectMemberLimitReached(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + projectId));

        int currentMembers = projectMemberRepository.findByProjectId(projectId).size();
        return currentMembers >= project.getRequiredMembers();
    }

    /**
     * 멤버의 프로젝트 참여 이력 조회
     */
    public List<ProjectMemberDTO> getMemberProjectHistory(Long memberId) {
        List<ProjectMember> projectMembers = projectMemberRepository.findByMemberId(memberId);
        return projectMembers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * ProjectMember 엔티티를 DTO로 변환
     */
    private ProjectMemberDTO convertToDTO(ProjectMember projectMember) {
        return ProjectMemberDTO.builder()
                .projectId(projectMember.getProject().getId())
                .projectName(projectMember.getProject().getName())
                .memberId(projectMember.getMember().getId())
                .memberName(projectMember.getMember().getName())
                .role(projectMember.getRole())
                .joinDate(projectMember.getJoinDate())
                .endDate(projectMember.getEndDate())
                .build();
    }

    /**
     * 프로젝트 멤버 참여 기간 체크
     */
    public boolean isActiveMember(Long projectId, Long memberId) {
        return projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .map(member -> member.getEndDate() == null)
                .orElse(false);
    }
}
