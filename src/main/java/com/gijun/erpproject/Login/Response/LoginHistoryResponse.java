package com.gijun.erpproject.Login.Response;

import com.gijun.erpproject.Entity.Login.LoginStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginHistoryResponse {
    private Long id;
    private String username;
    private String ipAddress;
    private String userAgent;
    private LoginStatus status;
    private String failReason;
    private LocalDateTime loginAt;
}