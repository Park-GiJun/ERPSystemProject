package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.*;
import com.gijun.erpproject.Enum.ProjectRole;
import com.gijun.erpproject.Enum.ProjectStatus;
import com.gijun.erpproject.Repository.*;
import com.gijun.erpproject.dto.ProjectDetailDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMilestoneRepository milestoneRepository;
    private final ProjectTaskRepository taskRepository;
    private final MemberService memberService;

    @Transactional
    public Project createProject(Project project, List<Long> memberIds) {
        Project savedProject = projectRepository.save(project);

        // 프로젝트 멤버 할당
        memberIds.forEach(memberId -> {
            Member member = memberService.getMemberById(memberId);
            ProjectMember projectMember = ProjectMember.builder()
                    .project(savedProject)
                    .member(member)
                    .role(ProjectRole.DEVELOPER)
                    .build();
            projectMemberRepository.save(projectMember);
        });

        return savedProject;
    }

    @Transactional
    public Project getProjectById(Long projectId) {
        return projectRepository.findByIdWithMembersAndTechnologies(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }


    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Transactional
    public void updateProjectStatus(Long projectId, ProjectStatus status) {
        Project project = getProjectById(projectId);
        project.updateStatus(status);
    }

    // 프로젝트 진행률 계산
    public double calculateProjectProgress(Long projectId) {
        List<ProjectTask> tasks = taskRepository.findActiveTasksByProjectId(projectId);
        if (tasks.isEmpty()) return 0.0;

        return tasks.stream()
                .mapToInt(ProjectTask::getProgress)
                .average()
                .orElse(0.0);
    }

    // 지연된 프로젝트 조회
    public List<Project> getDelayedProjects() {
        return projectRepository.findDelayedProjects(LocalDate.now());
    }


}