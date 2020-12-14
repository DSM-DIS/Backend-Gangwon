package com.example.auth_dis.paylod;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String id;
    private String password;
    private String username;
}
