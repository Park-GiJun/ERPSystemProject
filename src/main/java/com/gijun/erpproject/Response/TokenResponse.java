package com.gijun.erpproject.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String accessToken; // 'accessToken'으로 통일
    private String username;
    private String tokenType;   // 추가
    private String role;

    public static TokenResponse of(String token, String username, String role) {
        return TokenResponse.builder()
                .accessToken(token)
                .username(username)
                .tokenType("Bearer")
                .role(role)
                .build();
    }
}