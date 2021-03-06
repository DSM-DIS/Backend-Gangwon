package com.example.auth_dis.Controller;

import com.example.auth_dis.paylod.*;
import com.example.auth_dis.service.user.UserService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path="/user")
    public void SIGN_IN (@RequestBody @Valid UserRequest user) {
        System.out.println(user);
        userService.SIGN_IN(user);
    }

    @GetMapping(path="/user")
    public UserIdResponse GET_INFO_BY_ACCESS(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        System.out.println("accessToken : " + accessToken);
        return userService.GET_INFO_BY_ACCESS(accessToken);
    }
    @GetMapping(path="/user/username")
    public UserNameResponse GetUserName(HttpServletRequest request) {
        String accessToken = request.getHeader("userId");
        System.out.println("accessToken : " + accessToken);
        return userService.GetUserName(accessToken);
    }
    @PostMapping(path="/check/id")
    public StatusResponse CheckId(@RequestBody IdResponse id){
        return userService.CheckId(id);
    }

    @PostMapping(path="/check/username")
    public StatusResponse CheckUsername(@RequestBody NameResponse username){
        return userService.CheckUsername(username);
    }

}
