package com.gijun.erpproject.Entity.Login;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "login_attempts")
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private int attempts;

    @Column(nullable = false)
    private LocalDateTime lastAttempt;

    @Column
    private LocalDateTime blockedUntil;

    @Builder
    public LoginAttempt(String username, String ipAddress) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.attempts = 1;
        this.lastAttempt = LocalDateTime.now();
    }

    public void incrementAttempts() {
        this.attempts++;
        this.lastAttempt = LocalDateTime.now();
        if (this.attempts >= 5) {  // 5회 실패시 30분 차단
            this.blockedUntil = LocalDateTime.now().plusMinutes(30);
        }
    }

    public void reset() {
        this.attempts = 0;
        this.blockedUntil = null;
    }
}