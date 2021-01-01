package com.example.auth_dis.paylod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AccessTokenResponse {
    private String accessToken;

}
