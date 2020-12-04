package com.example.auth_dis.service.user;

import com.example.auth_dis.Domain.user.User;
import com.example.auth_dis.Domain.user.UserRepository;
import com.example.auth_dis.Exception.TokenInvalidException;
import com.example.auth_dis.jwt.JwtTokenUtil;
import com.example.auth_dis.paylod.UserInformationResponse;
import com.example.auth_dis.paylod.UserResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void SIGN_IN(UserResponse user) {
        userRepository.save(
                User.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .password(user.getPassword())
                        .build()
        );
    }

    @Override
    public UserInformationResponse GET_INFO_BY_ACCESS(String AccessToken) {
        String email;
        try {
            email = jwtTokenUtil.getId(AccessToken);
        } catch (IllegalArgumentException | SignatureException | ExpiredJwtException e) {
            throw new TokenInvalidException("토큰 값이 옳지 않음.");
        }
        logger.info(email);
        if (email != null) {
            User user = userRepository.findByEmail(email).orElseThrow();
            UserInformationResponse userInformationResponse = new UserInformationResponse(user.getName(), user.getEmail());
            return userInformationResponse;

        } else {
            throw new TokenInvalidException("토큰 값이 옳지 않음.");
        }
    }
}
