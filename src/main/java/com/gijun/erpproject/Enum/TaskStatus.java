package com.gijun.erpproject.Enum;

import lombok.Getter;

@Getter
public enum TaskStatus {
    TODO("할 일"),
    IN_PROGRESS("진행중"),
    REVIEW("검토중"),
    COMPLETED("완료"),
    BLOCKED("차단됨");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }
}
