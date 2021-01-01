package com.example.auth_dis.service.auth;

import com.example.auth_dis.paylod.AccessTokenResponse;
import com.example.auth_dis.paylod.AccountRequest;
import com.example.auth_dis.paylod.TokenResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    TokenResponse LOG_IN(AccountRequest accountRequest) throws Exception;

    ResponseEntity<?> LOG_OUT(String RefreshToken);

    AccessTokenResponse GET_ACCESS_BY_REFRESH(String RefreshToken);

}