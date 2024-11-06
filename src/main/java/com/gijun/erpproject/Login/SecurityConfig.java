package com.gijun.erpproject.Login;

import com.gijun.erpproject.Config.Handler.CustomAccessDeniedHandler;
import com.gijun.erpproject.Config.Handler.CustomAuthenticationEntryPoint;
import com.gijun.erpproject.Login.service.CustomUserDetailsService;
import com.gijun.erpproject.Service.MemberService;
import com.gijun.erpproject.filter.JwtAuthenticationFilter;
import com.gijun.erpproject.filter.ProjectRequestLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ProjectRequestLoggingFilter projectRequestLoggingFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // 인증이 필요없는 요청들
                        .requestMatchers(
                                "/",
                                "/&**",
                                "/index.html",
                                "/favicon.ico",
                                "/assets/**",
                                "/*.js",
                                "/*.css",
                                "/*.json",
                                "/*.ico",
                                "/images/**",
                                "/fonts/**"
                        ).permitAll()
                        // API 엔드포인트
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/register",
                                "/api/auth/refresh-token",
                                "/api/auth/validate-token"
                        ).permitAll()
                        // Swagger UI
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // 정적 리소스
                        .requestMatchers(
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        // 프로젝트 관련 API 권한 설정
                        .requestMatchers(HttpMethod.POST, "/api/projects/").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/projects/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/memebers").permitAll()
                        .requestMatchers("/api/teams").permitAll()
                        // 관리자 전용
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/members/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/members/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/teams/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(projectRequestLoggingFilter, JwtAuthenticationFilter.class); // 커스텀 필터 추가


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8080",
                "http://localhost:5173",
                "http://olm.life",
                "https://olm.life"    // HTTPS 추가
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}