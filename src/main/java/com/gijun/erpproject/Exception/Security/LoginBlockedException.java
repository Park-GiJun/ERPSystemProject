package com.gijun.erpproject.Exception.Security;

// 로그인 차단 예외
public class LoginBlockedException extends SecurityException {
    public LoginBlockedException(String message) {
        super(message);
    }
}
