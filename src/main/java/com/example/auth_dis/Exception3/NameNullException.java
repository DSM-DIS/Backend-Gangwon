package com.example.auth_dis.Exception3;

public class NameNullException extends RuntimeException{
    public NameNullException(){
        super("Name의 값이 비었습니다.");
    }
}
