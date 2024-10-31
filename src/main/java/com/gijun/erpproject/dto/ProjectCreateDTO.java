package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.*;
import lombok.*;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateDTO {
    @NotBlank(message = "프로젝트 이름은 필수입니다")
    private String name;

    private String description;

    @NotNull(message = "시작일은 필수입니다")
    private LocalDate startDate;

    @NotNull(message = "종료일은 필수입니다")
    private LocalDate endDate;

    @Min(value = 1, message = "최소 1명 이상의 인원이 필요합니다")
    private Integer requiredMembers;

    @NotNull(message = "계약금액은 필수입니다")
    @Min(value = 0, message = "계약금액은 0 이상이어야 합니다")
    private BigDecimal contractAmount;

    @NotEmpty(message = "최소 1명 이상의 멤버가 필요합니다")
    private List<ProjectMemberDTO> members;  // 멤버 정보를 담는 DTO로 변경

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectMemberDTO {
        private Long memberId;
        private String role;
    }

    public Project toEntity() {
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .requiredMembers(requiredMembers)
                .contractAmount(contractAmount)
                .build();
    }

    public List<Long> getMemberIds() {
        return members.stream()
                .map(ProjectMemberDTO::getMemberId)
                .collect(Collectors.toList());
    }
}