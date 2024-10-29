package com.gijun.erpproject.Login.controller;

import com.gijun.erpproject.Login.JwtTokenProvider;
import com.gijun.erpproject.Request.LoginRequest;
import com.gijun.erpproject.Response.TokenResponse;
import com.gijun.erpproject.Response.TokenValidationResponse;
import com.gijun.erpproject.Login.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그인", description = "username과 password를 이용해 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest servletRequest) {

        String ipAddress = getClientIp(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        TokenResponse token = authService.login(request, ipAddress, userAgent);
        return ResponseEntity.ok(token);
    }

//    @Operation(summary = "로그아웃", description = "현재 사용자를 로그아웃합니다.")
//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(HttpServletRequest request) {
//        String token = resolveToken(request);
//        authService.logout(token);
//        return ResponseEntity.ok().build();
//    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.substring(7);  // "Bearer " 제거
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                return ResponseEntity.ok(new TokenValidationResponse(true, username));
            }
            return ResponseEntity.ok(new TokenValidationResponse(false, null));
        } catch (Exception e) {
            return ResponseEntity.ok(new TokenValidationResponse(false, null));
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}