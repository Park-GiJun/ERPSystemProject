package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.Project;
import com.gijun.erpproject.Entity.ProjectTask;
import com.gijun.erpproject.Enum.ProjectStatus;
import com.gijun.erpproject.Enum.TaskStatus;
import com.gijun.erpproject.Repository.ProjectMemberRepository;
import com.gijun.erpproject.Repository.ProjectRepository;
import com.gijun.erpproject.Repository.ProjectTaskRepository;
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
public class ProjectAnalyticsService {
    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository taskRepository;
    private final ProjectMemberRepository memberRepository;

    // 프로젝트 현황 분석
    public Map<ProjectStatus, Long> analyzeProjectStatus() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .collect(Collectors.groupingBy(
                        Project::getStatus,
                        Collectors.counting()
                ));
    }

    // 멤버별 태스크 현황 분석
    public Map<Long, Map<TaskStatus, Long>> analyzeMemberTaskStatus(Long projectId) {
        List<ProjectTask> tasks = taskRepository.findActiveTasksByProjectId(projectId);
        return tasks.stream()
                .collect(Collectors.groupingBy(
                        task -> task.getAssignee().getId(),
                        Collectors.groupingBy(
                                ProjectTask::getStatus,
                                Collectors.counting()
                        )
                ));
    }

    // 프로젝트 지연 분석
    public List<Project> analyzeDelayedProjects() {
        return projectRepository.findDelayedProjects(LocalDate.now());
    }
}