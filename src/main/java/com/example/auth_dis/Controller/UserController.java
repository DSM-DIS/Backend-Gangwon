package com.example.auth_dis.Controller;

import com.example.auth_dis.Domain.user.User;
import com.example.auth_dis.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping(path="/user")
    public Map<String, Object> SIGN_IN (@RequestBody @Valid User user) {
        return  authService.SIGN_IN(user);
    }

    @GetMapping(path="/user")
    public Map<String, Object> GET_INFO_BY_ACCESS(@RequestHeader("Authorization") String AccessToken) {
        return authService.GET_INFO_BY_ACCESS(AccessToken);
    }

}
