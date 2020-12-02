package com.example.auth_dis.Exception;

public class IdTypeException extends RuntimeException {
    public IdTypeException(){
        super("아이디 형식이 맞지 않습니다.");
    }
}
