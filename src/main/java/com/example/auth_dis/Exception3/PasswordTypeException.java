package com.example.auth_dis.Exception3;

public class PasswordTypeException extends RuntimeException {
    public PasswordTypeException() {
        super("비밀번호 형식에 맞지않습니다.");
    }
}