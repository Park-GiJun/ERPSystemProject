package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.ProjectMilestone;
import com.gijun.erpproject.Entity.ProjectTask;
import com.gijun.erpproject.Enum.MilestoneStatus;
import com.gijun.erpproject.Repository.ProjectMilestoneRepository;
import com.gijun.erpproject.Repository.ProjectTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMilestoneService {
    private final ProjectMilestoneRepository milestoneRepository;
    private final ProjectTaskRepository taskRepository;

    @Transactional
    public ProjectMilestone createMilestone(ProjectMilestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public List<ProjectMilestone> getMilestonesByProject(Long projectId) {
        return milestoneRepository.findByProjectIdOrderByOrderIndex(projectId);
    }

    @Transactional
    public void updateMilestoneStatus(Long milestoneId, MilestoneStatus status) {
        ProjectMilestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found: " + milestoneId));
        milestone.updateStatus(status);
    }

    // 마일스톤 진행률 계산
    public double calculateMilestoneProgress(Long milestoneId) {
        List<ProjectTask> tasks = taskRepository.findByMilestoneId(milestoneId);
        if (tasks.isEmpty()) return 0.0;

        return tasks.stream()
                .mapToInt(ProjectTask::getProgress)
                .average()
                .orElse(0.0);
    }
}
