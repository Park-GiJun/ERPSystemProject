package com.gijun.erpproject.Login.service;

import com.gijun.erpproject.Entity.Login.LoginAttempt;
import com.gijun.erpproject.Entity.Login.LoginHistory;
import com.gijun.erpproject.Entity.Login.LoginStatus;
import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Exception.Security.LoginBlockedException;
import com.gijun.erpproject.Login.JwtTokenProvider;
import com.gijun.erpproject.Login.Repository.LoginAttemptRepository;
import com.gijun.erpproject.Login.Repository.LoginHistoryRepository;
import com.gijun.erpproject.Request.LoginRequest;
import com.gijun.erpproject.Response.TokenResponse;
import com.gijun.erpproject.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final LoginAttemptRepository loginAttemptRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인 시도 제한 관련 상수
    private static final int MAX_ATTEMPTS = 5;
    private static final int BLOCK_DURATION_MINUTES = 30;

    public TokenResponse login(LoginRequest request, String ipAddress, String userAgent) throws AuthenticationException {
        checkLoginAttempts(request.getUsername(), ipAddress);

        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));


        if(!memberRepository.findEnabledByUsername(member.getUsername()).orElse(false)) {
            throw new BadCredentialsException("비활성화된 계정입니다.");
        }


        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            recordFailedAttempt(member, ipAddress, userAgent, "잘못된 비밀번호입니다.");
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(member.getUsername(),
                Collections.singletonList(member.getRole().name()));

        saveLoginHistory(member, ipAddress, userAgent, LoginStatus.SUCCESS, null);
        resetLoginAttempts(request.getUsername(), ipAddress);
        member.updateLastLoginAt(LocalDateTime.now());

        return TokenResponse.of(token, member.getUsername(), String.valueOf(member.getRole()));  // 수정된 부분
    }

    private void checkLoginAttempts(String username, String ipAddress) {
        LoginAttempt attempt = loginAttemptRepository
                .findByUsernameAndIpAddress(username, ipAddress)
                .orElse(null);

        if (attempt != null) {
            if (attempt.getBlockedUntil() != null &&
                    attempt.getBlockedUntil().isAfter(LocalDateTime.now())) {
                long minutesLeft = ChronoUnit.MINUTES.between(
                        LocalDateTime.now(), attempt.getBlockedUntil());
                throw new LoginBlockedException(
                        String.format("Account is blocked for %d more minutes", minutesLeft));
            }

            if (attempt.getLastAttempt().plusHours(1).isBefore(LocalDateTime.now())) {
                attempt.reset();  // 마지막 시도로부터 1시간 경과시 초기화
            }
        }
    }

    public void recordFailedAttempt(Member member, String ipAddress, String userAgent, String failReason) {
        saveLoginHistory(member, ipAddress, userAgent, LoginStatus.FAIL, failReason);

        LoginAttempt attempt = loginAttemptRepository
                .findByUsernameAndIpAddress(member.getUsername(), ipAddress)
                .orElseGet(() -> LoginAttempt.builder()
                        .username(member.getUsername())
                        .ipAddress(ipAddress)
                        .build());

        attempt.incrementAttempts();

        loginAttemptRepository.save(attempt);
    }


    private void resetLoginAttempts(String username, String ipAddress) {
        loginAttemptRepository.findByUsernameAndIpAddress(username, ipAddress)
                .ifPresent(attempt -> {
                    attempt.reset();
                    loginAttemptRepository.save(attempt);
                });
    }

    private void saveLoginHistory(Member member, String ipAddress, String userAgent,
                                  LoginStatus status, String failReason) {
        LoginHistory history = LoginHistory.builder()
                .member(member)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .status(status)
                .failReason(failReason)
                .build();

        loginHistoryRepository.save(history);
    }
}