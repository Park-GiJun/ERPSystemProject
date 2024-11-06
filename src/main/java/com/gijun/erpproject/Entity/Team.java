package com.gijun.erpproject.Entity;

import com.gijun.erpproject.Entity.Login.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teams")
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String teamName;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_team_id")
    private Team parentTeam;  // 상위 팀 (부서)

    @Column(nullable = false)
    private Integer teamLevel;  // 팀 레벨 (1: 부서, 2: 팀, 3: 파트 등)

    @Builder
    public Team(String teamName, String description, Team parentTeam, Integer teamLevel) {
        this.teamName = teamName;
        this.description = description;
        this.parentTeam = parentTeam;
        this.teamLevel = teamLevel;
    }

    public void update(String teamName, String description, Team parentTeam, Integer teamLevel) {
        this.teamName = teamName;
        this.description = description;
        this.parentTeam = parentTeam;
        this.teamLevel = teamLevel;
    }
}
