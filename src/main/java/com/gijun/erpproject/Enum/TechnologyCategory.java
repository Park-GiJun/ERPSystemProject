package com.gijun.erpproject.Enum;

import lombok.Getter;

@Getter
public enum TechnologyCategory {
    FRONTEND("프론트엔드"),
    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    DEVOPS("데브옵스"),
    TOOLS("도구");

    private final String description;

    TechnologyCategory(String description) {
        this.description = description;
    }
}
