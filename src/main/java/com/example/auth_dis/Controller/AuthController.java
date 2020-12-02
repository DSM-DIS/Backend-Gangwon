package com.example.auth_dis.Controller;

import com.example.auth_dis.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

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
    public Map<String, Object> LOG_IN(@RequestBody Map<String, String> m) throws Exception {
        return authService.LOG_IN(m);
    }
    @PatchMapping(path="/auth")
    public Map<String, Object> GET_ACCESS_BY_REFRESH(@RequestHeader("refreshToken") String RefreshToken) {
        return authService.GET_ACCESS_BY_REFRESH(RefreshToken);
    }
}