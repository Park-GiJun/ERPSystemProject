package com.gijun.erpproject.Enum;

import lombok.Getter;

@Getter
public enum MilestoneStatus {
    PLANNED("계획됨"),
    IN_PROGRESS("진행중"),
    COMPLETED("완료"),
    DELAYED("지연"),
    CANCELLED("취소");

    private final String description;

    MilestoneStatus(String description) {
        this.description = description;
    }
}
