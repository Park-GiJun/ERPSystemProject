package com.gijun.erpproject.Entity;

import com.gijun.erpproject.Entity.Login.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gijun.erpproject.Enum.TaskStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_tasks")
public class ProjectTask extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private ProjectMilestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private Member assignee;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDate plannedStartDate;

    @Column(nullable = false)
    private LocalDate plannedEndDate;

    @Column
    private LocalDate actualStartDate;

    @Column
    private LocalDate actualEndDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private Integer progress; // 진행률 (0-100)

    @Column(nullable = false)
    private Integer priority; // 우선순위 (1-5)

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskComment> comments = new ArrayList<>();

    @Builder
    public ProjectTask(ProjectMilestone milestone, Member assignee, String title,
                       String description, LocalDate plannedStartDate,
                       LocalDate plannedEndDate, Integer priority) {
        this.milestone = milestone;
        this.assignee = assignee;
        this.title = title;
        this.description = description;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.priority = priority;
        this.status = TaskStatus.TODO;
        this.progress = 0;
    }

    public void updateProgress(Integer progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100");
        }
        this.progress = progress;
        updateStatusBasedOnProgress();
    }

    private void updateStatusBasedOnProgress() {
        if (progress == 0) {
            this.status = TaskStatus.TODO;
        } else if (progress == 100) {
            this.status = TaskStatus.COMPLETED;
        } else {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }

    public void updateStatus(TaskStatus status) {
        this.status = status;
    }
}
