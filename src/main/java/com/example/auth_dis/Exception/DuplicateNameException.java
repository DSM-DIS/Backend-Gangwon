package com.example.auth_dis.Exception;

public class DuplicateNameException extends RuntimeException{
    public DuplicateNameException(){
        super("닉네임의 값이 중복입니다.");
    }
}