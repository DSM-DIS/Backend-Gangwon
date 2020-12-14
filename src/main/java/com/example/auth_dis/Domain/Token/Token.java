package com.example.auth_dis.Domain.Token;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@RedisHash(value = "refresh_token")
public class Token {

    @Id
    private String username;

    @Indexed
    private String refreshToken;

    public Token update(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }}