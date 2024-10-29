package com.gijun.erpproject.Login.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String accessToken; // 'accessToken'으로 통일
    private String username;
    private String tokenType;   // 추가

    public static TokenResponse of(String token, String username) {
        return TokenResponse.builder()
                .accessToken(token)
                .username(username)
                .tokenType("Bearer")
                .build();
    }
}