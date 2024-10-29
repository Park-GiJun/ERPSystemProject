package com.gijun.erpproject.Exception.Security;

// 유효하지 않은 토큰 예외
public class InvalidTokenException extends SecurityException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
