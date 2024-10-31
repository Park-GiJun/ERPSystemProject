package com.gijun.erpproject.Entity;

import com.gijun.erpproject.Entity.Login.BaseTimeEntity;
import com.gijun.erpproject.Enum.TechnologyCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_technologies")
public class ProjectTechnology extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechnologyCategory category;

    @Column(nullable = false)
    private String technologyName;

    @Column
    private String version;

    @Builder
    public ProjectTechnology(Project project, TechnologyCategory category,
                             String technologyName, String version) {
        this.project = project;
        this.category = category;
        this.technologyName = technologyName;
        this.version = version;
    }
}