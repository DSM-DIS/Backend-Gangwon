package com.example.auth_dis.service.user;

import com.example.auth_dis.Domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Override
    public Map<String, Object> SIGN_IN(User account) {
        return null;
    }

    @Override
    public Map<String, Object> GET_INFO_BY_ACCESS(String AccessToken) {
        return null;
    }
}
