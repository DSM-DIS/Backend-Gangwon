package com.example.auth_dis.service.user;

import com.example.auth_dis.Domain.user.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> SIGN_IN(User account);
    Map<String, Object> GET_INFO_BY_ACCESS(String AccessToken);


}
