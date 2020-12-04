package com.example.auth_dis.Controller;

import com.example.auth_dis.paylod.AccountRequest;
import com.example.auth_dis.service.auth.AuthService;
import com.example.auth_dis.paylod.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    private final AuthService authService;

    @Transactional
    @DeleteMapping(path="/auth")
    public ResponseEntity<?> LOG_OUT(@RequestHeader("refreshToken") String RefreshToken) {
        return authService.LOG_OUT(RefreshToken);
    }
    @PostMapping(path = "/auth")
    public TokenResponse LOG_IN(@RequestBody AccountRequest accountRequest) throws Exception {
        return authService.LOG_IN(accountRequest);
    }
    @PatchMapping(path="/auth")
    public TokenResponse GET_ACCESS_BY_REFRESH(@RequestHeader("refreshToken") String RefreshToken) {
        return authService.GET_ACCESS_BY_REFRESH(RefreshToken);
    }
    
    @PostMapping("/user/test")
    public String test() {
        return "test";
    }
}
