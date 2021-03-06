package com.example.auth_dis.Controller;

import com.example.auth_dis.paylod.AccessTokenResponse;
import com.example.auth_dis.paylod.AccountRequest;
import com.example.auth_dis.service.auth.AuthService;
import com.example.auth_dis.paylod.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final AuthService authService;

    @Transactional
    @DeleteMapping(path="/auth")
    public ResponseEntity<?> LOG_OUT(@RequestHeader("userId") String RefreshToken) {
        System.out.println(RefreshToken);
        return authService.LOG_OUT(RefreshToken);
    }
    @PostMapping(path = "/auth")
    public TokenResponse LOG_IN(@RequestBody AccountRequest accountRequest) throws Exception {
        System.out.println("작동_컨트롤러");
        return authService.LOG_IN(accountRequest);
    }
    @PatchMapping(path="/auth")
        public AccessTokenResponse GET_ACCESS_BY_REFRESH(@RequestHeader("Authorization") String RefreshToken) {
        System.out.println(RefreshToken);
        return authService.GET_ACCESS_BY_REFRESH(RefreshToken);
    }
    
    @PostMapping("/user/test")
    public String test() {
        return "test";
    }
}
