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
@Table(name = "team_organization")
public class TeamOrganization extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false)
    private String position;  // 직책 (팀장, 팀원 등)

    @Column
    private String responsibility;  // 담당 업무

    @Column(nullable = false)
    private boolean isTeamLeader;  // 팀장 여부

    @Builder
    public TeamOrganization(Member member, Team team, String position,
                            String responsibility, boolean isTeamLeader) {
        this.member = member;
        this.team = team;
        this.position = position;
        this.responsibility = responsibility;
        this.isTeamLeader = isTeamLeader;
    }
}
