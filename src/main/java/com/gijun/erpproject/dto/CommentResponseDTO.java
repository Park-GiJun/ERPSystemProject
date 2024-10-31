package com.gijun.erpproject.dto;

import com.gijun.erpproject.Entity.TaskComment;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private MemberSimpleDTO member;
    private LocalDate createdAt;

    public static CommentResponseDTO from(TaskComment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .member(MemberSimpleDTO.from(comment.getMember()))
                .createdAt(comment.getCreatedAt().toLocalDate())
                .build();
    }
}
