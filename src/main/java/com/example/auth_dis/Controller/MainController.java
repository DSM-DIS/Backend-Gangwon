package com.example.auth_dis.Controller;

import com.example.auth_dis.Domain.Account;
import com.example.auth_dis.Repository.AccountRepository;
import com.example.auth_dis.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping // This means URL's start with /demo (after Application path)
public class MainController {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private final AuthService authService;

    @Transactional
    @PostMapping(path="/user")
    public Map<String, Object> SIGN_IN (@RequestBody @Valid Account account) {
        return  authService.SIGN_IN(account);
    }

    @GetMapping(path="/user")
    public Map<String, Object> GET_INFO_BY_ACCESS(@RequestHeader("Authorization") String AccessToken) {
        return authService.GET_INFO_BY_ACCESS(AccessToken);
    }

    @DeleteMapping(path="/auth")
    public ResponseEntity<?> LOG_OUT(@RequestHeader("refreshToken") String RefreshToken) {
        return authService.LOG_OUT(RefreshToken);
    }

    @PostMapping(path = "/auth")
    public Map<String, Object> LOG_IN(@RequestBody Map<String, String> m) throws Exception {
        return authService.LOG_IN(m);
    }
    @PatchMapping(path="/auth")
    public Map<String, Object> GET_ACCESS_BY_REFRESH(@RequestHeader("refreshToken") String RefreshToken) {
        return authService.GET_ACCESS_BY_REFRESH(RefreshToken);
    }
}