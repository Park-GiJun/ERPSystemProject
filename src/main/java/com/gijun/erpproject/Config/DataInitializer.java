package com.gijun.erpproject.Config;

import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Enum.Role;
import com.gijun.erpproject.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // admin 계정이 없을 경우에만 생성
        if (!memberRepository.existsByUsername("admin")) {
            Member admin = Member.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin1234!"))
                    .name("관리자")
                    .email("admin@erp.com")
                    .role(Role.ROLE_ADMIN)
                    .build();

            memberRepository.save(admin);
        }
    }
}