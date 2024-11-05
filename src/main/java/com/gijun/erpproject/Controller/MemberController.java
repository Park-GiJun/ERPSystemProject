package com.gijun.erpproject.Controller;

import com.gijun.erpproject.Service.MemberService;
import com.gijun.erpproject.dto.MemberDTO;
import com.gijun.erpproject.Response.PageResponseDTO;
import com.gijun.erpproject.dto.register.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/members")
    public ResponseEntity<PageResponseDTO<MemberDTO>> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(memberService.getAllMembers(page, size));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> registerMember(@RequestBody RegisterRequestDTO registerRequestDTO) {

        memberService.register(registerRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/members/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(@PathVariable Long id) {

        memberService.resetPassword(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/{id}/toggle-status")
    public ResponseEntity<Void> toggleStatus(@PathVariable Long id) {

        memberService.toggleMemberStatus(id);
        return ResponseEntity.ok().build();
    }
}