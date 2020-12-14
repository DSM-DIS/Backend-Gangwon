package com.example.auth_dis.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.annotation.Id;
import java.io.Serializable;

@Getter
@Setter
@RedisHash(value = "refresh_token")
@AllArgsConstructor
public class Token {
    @Id
    private String username;
    @Indexed
    private String refreshToken;

}
