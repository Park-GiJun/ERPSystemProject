package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Enum.Role;
import com.gijun.erpproject.Exception.Security.DuplicateLoginException;
import com.gijun.erpproject.Repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member join(MemberJoinRequest request) {
        validateDuplicateMember(request);

        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .role(Role.ROLE_USER)
                .build();

        return memberRepository.save(member);
    }

    private void validateDuplicateMember(MemberJoinRequest request) {
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateLoginException("이미 사용중인 아이디입니다.");
        }
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateLoginException("이미 사용중인 이메일입니다.");
        }
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }
}

