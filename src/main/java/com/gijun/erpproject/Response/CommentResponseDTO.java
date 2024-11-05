package com.gijun.erpproject.Response;

import com.gijun.erpproject.Entity.TaskComment;
import com.gijun.erpproject.dto.MemberSimpleDTO;
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
