package com.gijun.erpproject.dto;
import com.gijun.erpproject.Entity.Member;
import com.gijun.erpproject.Entity.ProjectTask;
import com.gijun.erpproject.Entity.TaskComment;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDTO {
    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(max = 1000, message = "댓글은 1000자를 초과할 수 없습니다")
    private String content;

    public TaskComment toEntity(ProjectTask task, Member member) {
        return TaskComment.builder()
                .task(task)
                .member(member)
                .content(content)
                .build();
    }
}
