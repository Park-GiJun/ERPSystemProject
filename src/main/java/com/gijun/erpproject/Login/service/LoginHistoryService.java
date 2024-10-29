package com.gijun.erpproject.Login.service;

import com.gijun.erpproject.Entity.Login.LoginHistory;
import com.gijun.erpproject.Entity.Login.LoginStatus;
import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Login.Repository.LoginHistoryRepository;
import com.gijun.erpproject.Response.LoginHistoryResponse;
import com.gijun.erpproject.Repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final MemberRepository memberRepository;

    public Page<LoginHistoryResponse> getMyLoginHistory(int page, int size) {
        // SecurityContext에서 현재 로그인한 사용자의 username 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return loginHistoryRepository.findByMember(member, PageRequest.of(page, size))
                .map(this::convertToLoginHistoryResponse);
    }

    private LoginHistoryResponse convertToLoginHistoryResponse(LoginHistory history) {
        return LoginHistoryResponse.builder()
                .id(history.getId())
                .username(history.getMember().getUsername())
                .ipAddress(history.getIpAddress())
                .userAgent(history.getUserAgent())
                .status(history.getStatus())
                .loginAt(history.getCreatedAt())
                .build();
    }

    public Page<LoginHistoryResponse> getLoginHistory(
            String username, LoginStatus status, LocalDate startDate, LocalDate endDate, int page, int size) {

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

        return loginHistoryRepository.findBySearchCriteria(
                        username, status, startDateTime, endDateTime, PageRequest.of(page, size))
                .map(this::convertToResponse);
    }

    private LoginHistoryResponse convertToResponse(LoginHistory history) {
        return LoginHistoryResponse.builder()
                .id(history.getId())
                .username(history.getMember().getUsername())
                .ipAddress(history.getIpAddress())
                .userAgent(history.getUserAgent())
                .status(history.getStatus())
                .failReason(history.getFailReason())
                .loginAt(history.getCreatedAt())
                .build();
    }
}

