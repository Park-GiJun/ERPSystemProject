package com.gijun.erpproject.Service;

import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Enum.Role;
import com.gijun.erpproject.Exception.Security.DuplicateLoginException;
import com.gijun.erpproject.Repository.MemberRepository;
import com.gijun.erpproject.dto.MemberDTO;
import com.gijun.erpproject.Response.PageResponseDTO;
import com.gijun.erpproject.dto.register.RegisterRequestDTO;
import com.gijun.erpproject.dto.register.RegisterResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public PageResponseDTO<MemberDTO> getAllMembers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Member> memberPage = memberRepository.findAll(pageable);

        List<MemberDTO> memberDTOs = memberPage.getContent().stream()
                .map(MemberDTO::from)
                .collect(Collectors.toList());

        return new PageResponseDTO<>(
                memberDTOs,
                memberPage.getTotalElements(),
                memberPage.getTotalPages(),
                memberPage.getNumber(),
                memberPage.getSize(),
                memberPage.isFirst(),
                memberPage.isLast()
        );
    }

    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        // 1. 중복 검사
        validateDuplicateMember(request);

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());

        // 3. Member 엔티티 생성
        Member member = Member.builder()
                .username(request.username())
                .password(encodedPassword)
                .name(request.name())
                .email(request.email())
                .role(Role.valueOf(request.role()))  // 분리된 Role enum 사용
                .enabled(true)
                .build();

        // 4. 저장
        Member savedMember = memberRepository.save(member);

        // 5. DTO 변환 후 반환
        return RegisterResponseDTO.from(savedMember);
    }

    /**
     * 회원 목록 조회 (페이징)
     */
    public Page<Member> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    /**
     * 회원 상태 토글
     */
    @Transactional
    public void toggleMemberStatus(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        member.toggleEnabled();
    }

    /**
     * 비밀번호 초기화
     */
    @Transactional
    public void resetPassword(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        // 기본 비밀번호로 초기화 (예: "password1234")
        String encodedPassword = passwordEncoder.encode("password1234");
        member.updatePassword(encodedPassword);
    }

    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(RegisterRequestDTO request) {
        // 사용자명 중복 검사
        if (memberRepository.existsByUsername(request.username())) {
            throw new IllegalStateException("이미 사용 중인 사용자명입니다.");
        }

        // 이메일 중복 검사
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }
    }

    /**
     * 회원 조회 (ID)
     */
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));
    }

    /**
     * 회원 존재 여부 확인 (사용자명)
     */
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    /**
     * 회원 존재 여부 확인 (이메일)
     */
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}

