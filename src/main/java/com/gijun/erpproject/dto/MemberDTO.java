package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.Member;

import java.time.LocalDateTime;

public record MemberDTO(
        Long id,
        String username,
        String name,
        String email,
        String role,
        boolean enabled,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static MemberDTO from(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getUsername(),
                member.getName(),
                member.getEmail(),
                member.getRole().name(),
                member.isEnabled(),
                member.getLastLoginAt(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}
