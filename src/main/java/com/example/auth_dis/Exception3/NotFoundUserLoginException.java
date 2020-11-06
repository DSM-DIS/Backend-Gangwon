package com.example.auth_dis.Exception3;

public class NotFoundUserLoginException extends RuntimeException {
    public NotFoundUserLoginException(){
        super("아이디 혹은 비밀번호가 옳지 않습니다.");
    }

}
