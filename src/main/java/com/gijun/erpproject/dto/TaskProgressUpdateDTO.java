package com.gijun.erpproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskProgressUpdateDTO {
    @Min(value = 0, message = "진행률은 0 이상이어야 합니다")
    @Max(value = 100, message = "진행률은 100 이하여야 합니다")
    private Integer progress;
}
