package com.gijun.erpproject.Entity;

import com.gijun.erpproject.Entity.Login.BaseTimeEntity;
import com.gijun.erpproject.Enum.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_members")
public class ProjectMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectRole role;

    @Column(nullable = false)
    private LocalDate joinDate;

    @Column
    private LocalDate endDate;

    @Builder
    public ProjectMember(Project project, Member member, ProjectRole role) {
        this.project = project;
        this.member = member;
        this.role = role;
        this.joinDate = LocalDate.now();
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

