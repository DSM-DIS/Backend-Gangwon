package com.example.auth_dis.Exception;

public class IdNullException extends RuntimeException{
    public IdNullException(){
        super("Id의 값이 비었습니다.");
    }
}

