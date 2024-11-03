package com.gijun.erpproject.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.gijun.erpproject.Entity.Login.BaseTimeEntity;
import com.gijun.erpproject.Enum.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "projects")
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer requiredMembers; // 필요 인원 수

    @Column(nullable = false)
    private BigDecimal contractAmount; // 계약금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    // 프로젝트 멤버 관리를 위한 중간 테이블 엔티티와의 관계
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectMember> projectMembers = new HashSet<>();

    // 기술 스택 관리를 위한 중간 테이블 엔티티와의 관계
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectTechnology> technologies = new HashSet<>();

    @Builder
    public Project(String name, String description, LocalDate startDate, LocalDate endDate,
                   Integer requiredMembers, BigDecimal contractAmount) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredMembers = requiredMembers;
        this.contractAmount = contractAmount;
        this.status = ProjectStatus.PLANNED;
    }

    // 프로젝트 상태 업데이트
    public void updateStatus(ProjectStatus status) {
        this.status = status;
    }

    // 프로젝트 정보 업데이트
    public void updateProject(String name, String description, LocalDate startDate,
                              LocalDate endDate, Integer requiredMembers, BigDecimal contractAmount) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredMembers = requiredMembers;
        this.contractAmount = contractAmount;
    }
}