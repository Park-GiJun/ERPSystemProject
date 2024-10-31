package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.*;
import com.gijun.erpproject.Enum.ProjectRole;
import com.gijun.erpproject.Enum.ProjectStatus;
import com.gijun.erpproject.Repository.*;
import lombok.RequiredArgsConstructor;
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

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));
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