package com.gijun.erpproject.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class ProjectRequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 프로젝트 관련 엔드포인트에 대한 로그를 찍는 조건
        if (request.getRequestURI().startsWith("/api/projects")) {
            log.debug("프로젝트 관련 요청 - URI: {}, 메소드: {}", request.getRequestURI(), request.getMethod());
        }

        filterChain.doFilter(request, response);
    }
}

