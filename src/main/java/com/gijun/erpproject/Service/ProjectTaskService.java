package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Entity.ProjectTask;
import com.gijun.erpproject.Entity.TaskComment;
import com.gijun.erpproject.Enum.TaskStatus;
import com.gijun.erpproject.Repository.ProjectMilestoneRepository;
import com.gijun.erpproject.Repository.ProjectRepository;
import com.gijun.erpproject.Repository.ProjectTaskRepository;
import com.gijun.erpproject.Repository.TaskCommentRepository;
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
public class ProjectTaskService {
    private final ProjectTaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMilestoneRepository milestoneRepository;
    private final TaskCommentRepository commentRepository;
    private final MemberService memberService;

    @Transactional
    public ProjectTask createTask(ProjectTask task) {
        return taskRepository.save(task);
    }

    public List<ProjectTask> getTasksByMilestone(Long milestoneId) {
        return taskRepository.findByMilestoneId(milestoneId);
    }

    @Transactional
    public void updateTaskProgress(Long taskId, Integer progress) {
        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
        task.updateProgress(progress);
    }

    @Transactional
    public void updateTaskStatus(Long taskId, TaskStatus status) {
        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
        task.updateStatus(status);
    }

    @Transactional
    public TaskComment addComment(Long taskId, String content, String username) {
        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        Member member = memberService.findByUsername(username);

        TaskComment comment = TaskComment.builder()
                .task(task)
                .member(member)
                .content(content)
                .build();

        return commentRepository.save(comment);
    }

    public List<TaskComment> getTaskComments(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
    public List<ProjectTask> getTasksByProject(Long projectId) {
        // 프로젝트 존재 여부 확인
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project not found with id: " + projectId);
        }
        return taskRepository.findByProjectId(projectId);
    }

    /**
     * 프로젝트의 상태별 태스크 조회
     */
    public List<ProjectTask> getTasksByProjectAndStatus(Long projectId, TaskStatus status) {
        return taskRepository.findByProjectIdAndStatus(projectId, status);
    }

    /**
     * 프로젝트의 우선순위별 태스크 조회
     */
    public List<ProjectTask> getTasksByProjectOrderByPriority(Long projectId) {
        return taskRepository.findByProjectIdOrderByPriorityAndEndDate(projectId);
    }

    /**
     * 프로젝트의 태스크 통계 조회
     */
    public Map<TaskStatus, Long> getProjectTaskStatistics(Long projectId) {
        List<ProjectTask> tasks = getTasksByProject(projectId);
        return tasks.stream()
                .collect(Collectors.groupingBy(
                        ProjectTask::getStatus,
                        Collectors.counting()
                ));
    }

    /**
     * 프로젝트의 전체 태스크 진행률 계산
     */
    public double calculateProjectTaskProgress(Long projectId) {
        List<ProjectTask> tasks = getTasksByProject(projectId);
        if (tasks.isEmpty()) {
            return 0.0;
        }

        return tasks.stream()
                .mapToInt(ProjectTask::getProgress)
                .average()
                .orElse(0.0);
    }

    /**
     * 지연된 태스크 조회
     */
    public List<ProjectTask> getDelayedTasks(Long projectId) {
        LocalDate now = LocalDate.now();
        return getTasksByProject(projectId).stream()
                .filter(task -> task.getStatus() != TaskStatus.COMPLETED &&
                        task.getPlannedEndDate().isBefore(now))
                .collect(Collectors.toList());
    }


}