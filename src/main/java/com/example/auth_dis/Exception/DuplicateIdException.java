package com.example.auth_dis.Exception;

public class DuplicateIdException extends RuntimeException{
    public DuplicateIdException(){
        super("Id의 값이 중복입니다.");
    }
}
