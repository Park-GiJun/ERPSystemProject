package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.Member;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSimpleDTO {
    private Long id;
    private String username;
    private String name;

    public static MemberSimpleDTO from(Member member) {
        return MemberSimpleDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .build();
    }
}
