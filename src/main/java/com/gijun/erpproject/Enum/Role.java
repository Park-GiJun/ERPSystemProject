package com.gijun.erpproject.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("관리자");

    private final String description;
}