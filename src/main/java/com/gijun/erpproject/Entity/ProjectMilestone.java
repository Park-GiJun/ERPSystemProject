package com.gijun.erpproject.Entity;

import com.gijun.erpproject.Entity.Login.BaseTimeEntity;
import com.gijun.erpproject.Enum.MilestoneStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_milestones")
public class ProjectMilestone extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

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
    private MilestoneStatus status;

    @Column(nullable = false)
    private Integer orderIndex; // 마일스톤 순서

    @Column(nullable = false)
    private double progress;


    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    private List<ProjectTask> tasks = new ArrayList<>();

    @Builder
    public ProjectMilestone(Project project, String title, String description,
                            LocalDate plannedStartDate, LocalDate plannedEndDate,
                            Integer orderIndex) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.orderIndex = orderIndex;
        this.status = MilestoneStatus.PLANNED;
    }

    public void updateStatus(MilestoneStatus status) {
        this.status = status;
    }

    public void updateDates(LocalDate actualStartDate, LocalDate actualEndDate) {
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
    }

    public void updateProgress(double progress) {
        this.progress = progress;
    }
}