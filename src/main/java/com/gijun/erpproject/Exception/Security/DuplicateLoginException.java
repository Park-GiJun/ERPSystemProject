package com.gijun.erpproject.Exception.Security;

// 중복 로그인 예외
public class DuplicateLoginException extends SecurityException {
    public DuplicateLoginException(String message) {
        super(message);
    }
}
