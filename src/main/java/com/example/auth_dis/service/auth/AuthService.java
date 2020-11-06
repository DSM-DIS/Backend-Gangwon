package com.example.auth_dis.service.auth;

import com.example.auth_dis.Domain.Account;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    Map<String, Object> LOG_IN(Map<String, String> m) throws Exception;
    Map<String, Object> SIGN_IN(Account account);
    Map<String, Object> GET_INFO_BY_ACCESS(String AccessToken);
    ResponseEntity<?> LOG_OUT(String RefreshToken);
    Map<String, Object> GET_ACCESS_BY_REFRESH(String RefreshToken);



    }