package com.gijun.erpproject.Entity.Login;

import com.gijun.erpproject.Entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "login_history")
public class LoginHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginStatus status;

    @Column
    private String failReason;

    @Builder
    public LoginHistory(Member member, String ipAddress, String userAgent, LoginStatus status, String failReason) {
        this.member = member;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.status = status;
        this.failReason = failReason;
    }
}