package com.example.auth_dis.Exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(){
        super("ID를 규칙에 맞게 다시 작성해주세요.");
    }
}

