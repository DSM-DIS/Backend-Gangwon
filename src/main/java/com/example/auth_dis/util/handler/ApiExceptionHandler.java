package com.example.auth_dis.util.handler;


import com.example.auth_dis.Exception.*;
import com.example.auth_dis.util.forms.ApiErrorResponseForm;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(PasswordNotFoundException.class)
    public ResponseEntity<ApiErrorResponseForm> PasswordNotFoundException(PasswordNotFoundException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Password Mismatch Exception", "비밀번호가 일치하는 계정을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ApiErrorResponseForm> IdNotFoundException(IdNotFoundException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Id Mismatch Exception", "아이디가 일치하는 계정을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PasswordNullException.class)
    public ResponseEntity<ApiErrorResponseForm> PasswordNullException(PasswordNullException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Password null Exception", "비밀번호의 값이 비었음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IdNullException.class)
    public ResponseEntity<ApiErrorResponseForm> IdNullException(IdNullException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Id null Exception", "아이디의 값이 비었음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NameNullException.class)
    public ResponseEntity<ApiErrorResponseForm> NameNullException(NameNullException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Name null Exception", "닉네임의 값이 비었음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ApiErrorResponseForm> tokenInvalidExceptionHandler(TokenInvalidException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Token Invalid Exception", "토큰이 잘못됨");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ApiErrorResponseForm> tokenExpirationExceptionHandler(TokenExpirationException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Token Expiration Exception", "토큰이 만료됨");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorResponseForm> numberFormatExceptionHandler(NumberFormatException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Number Format Exception", "요청의 값이 잘못됨");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorResponseForm> noSuchElementExceptionHandler(NoSuchElementException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No Such Element Exception", "일치하는 요소가 존재하지 않음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<ApiErrorResponseForm> DuplicateNameException(DuplicateNameException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No Such Element Exception", "이름이 중복됨.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<ApiErrorResponseForm> DuplicateIdException(DuplicateIdException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No Such Element Exception", "아이디가 중복됨.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PasswordTypeException.class)
    public ResponseEntity<ApiErrorResponseForm> PasswordTypeException(PasswordTypeException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No Such Element Exception", "비밀번호 형식이 맞지않음.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IdTypeException.class)
    public ResponseEntity<ApiErrorResponseForm> IdTypeException(IdTypeException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No Such Element Exception", "아이디 형식이 맞지않음.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NotFoundUserLoginException.class)
    public ResponseEntity<ApiErrorResponseForm> NotFoundUserLoginException(NotFoundUserLoginException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No Such Element Exception", "아이디 혹은 비밀번호가 틀림.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiErrorResponseForm> SignatureException(SignatureException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No match JWT token", "토큰의 값이 옳지 않습니다.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
