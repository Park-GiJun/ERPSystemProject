package com.gijun.erpproject.Enum;

import lombok.Getter;

@Getter
public enum ProjectRole {
    PM("프로젝트 매니저"),
    TEAM_LEAD("팀 리더"),
    DEVELOPER("개발자"),
    DESIGNER("디자이너"),
    QA("품질 관리자");

    private final String description;

    ProjectRole(String description) {
        this.description = description;
    }
}
