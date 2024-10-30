package com.gijun.erpproject.Entity;

import com.gijun.erpproject.Entity.Login.BaseTimeEntity;
import com.gijun.erpproject.Entity.Login.LoginHistory;
import com.gijun.erpproject.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Getter
    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<LoginHistory> loginHistories = new ArrayList<>();

    @Builder
    public Member(String username, String password, String name, String email, Role role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }

    public void updateLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    // 회원 상태 토글
    public void toggleEnabled() {
        this.enabled = !this.enabled;
    }

    // 비밀번호 업데이트
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    // 마지막 로그인 시간 업데이트
    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

    // 회원 정보 업데이트
    public void update(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}