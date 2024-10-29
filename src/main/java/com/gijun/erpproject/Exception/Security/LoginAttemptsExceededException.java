package com.gijun.erpproject.Exception.Security;

// 로그인 시도 초과 예외
public class LoginAttemptsExceededException extends SecurityException {
    public LoginAttemptsExceededException(String message) {
        super(message);
    }
}
