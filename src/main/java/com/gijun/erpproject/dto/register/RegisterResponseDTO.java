package com.gijun.erpproject.dto.register;

import com.gijun.erpproject.Entity.Member;

import java.time.LocalDateTime;

public record RegisterResponseDTO(
        Long id,
        String username,
        String name,
        String email,
        String role,  // Role enum의 name()으로 변환하여 반환
        boolean enabled,
        LocalDateTime createdAt
) {
    public static RegisterResponseDTO from(Member member) {
        return new RegisterResponseDTO(
                member.getId(),
                member.getUsername(),
                member.getName(),
                member.getEmail(),
                member.getRole().name(),  // Role enum을 문자열로 변환
                member.isEnabled(),
                member.getCreatedAt()
        );
    }
}