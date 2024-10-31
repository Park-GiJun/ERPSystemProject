package com.gijun.erpproject.Enum;

import lombok.Getter;

@Getter
public enum ProjectStatus {
    PLANNED("계획"),
    IN_PROGRESS("진행중"),
    COMPLETED("완료"),
    ON_HOLD("보류"),
    CANCELLED("취소");

    private final String description;

    ProjectStatus(String description) {
        this.description = description;
    }
}
