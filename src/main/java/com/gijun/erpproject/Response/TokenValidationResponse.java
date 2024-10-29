package com.gijun.erpproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenValidationResponse {
    private boolean valid;
    private String username;
}