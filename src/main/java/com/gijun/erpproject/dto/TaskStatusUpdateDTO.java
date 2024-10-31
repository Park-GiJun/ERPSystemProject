package com.gijun.erpproject.dto;
import com.gijun.erpproject.Enum.TaskStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusUpdateDTO {
    @NotNull(message = "상태는 필수입니다")
    private TaskStatus status;
}
