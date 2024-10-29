package com.gijun.erpproject.Exception.Security;

// 기본 보안 예외
public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}

