package com.gijun.erpproject.dto.register;

public record RegisterRequestDTO(
        String username,
        String password,
        String name,
        String email,
        String role  // "ROLE_ADMIN" 또는 "ROLE_USER" 문자열로 받음
) {}
