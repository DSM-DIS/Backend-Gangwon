package com.example.auth_dis.Exception3;

public class PasswordNotFoundException extends RuntimeException {
    public PasswordNotFoundException(){
        super("Password를 규칙에 맞게 다시 작성해주세요.");
    }
}
