package com.example.auth_dis.service.auth;

import com.example.auth_dis.Domain.Token;
import com.example.auth_dis.Domain.user.User;
import com.example.auth_dis.Domain.user.UserRepository;
import com.example.auth_dis.Exception.PasswordNotFoundException;
import com.example.auth_dis.Exception.TokenInvalidException;
import com.example.auth_dis.Exception.UserNotFoundException;
import com.example.auth_dis.jwt.JwtTokenUtil;

import com.example.auth_dis.paylod.AccountRequest;
import com.example.auth_dis.paylod.TokenResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    private final UserRepository userRepository;
    @Autowired
    final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse LOG_IN(AccountRequest request) {
        final String accessToken = jwtTokenUtil.generateAccessToken(request.getId());
        final String refreshToken = jwtTokenUtil.generateRefreshToken(request.getId());

        User user = userRepository.findById(request.getId()).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println(request.getPassword() + ": : " + user.getPassword());
            throw new PasswordNotFoundException();
        }
        Token retok = new Token();
        retok.setUsername(request.getId());
        retok.setRefreshToken(refreshToken);
        try {
//            ValueOperations<String, Object> vop = redisTemplate.opsForValue();
//            vop.set(request.getId(), retok);
        }catch (Exception e){
            System.out.println("이거 왜안되냐고");
        }
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }



    @Override
    public ResponseEntity<?> LOG_OUT(String RefreshToken) {
        String email;
        String refreshToken = RefreshToken;
        try {
            email = jwtTokenUtil.getId(refreshToken);
        } catch (ExpiredJwtException e) { //expire됐을 때
            email = e.getClaims().getSubject();
            logger.info("username from expired access token: " + email);
        }
        User user = userRepository.findById(email)
                .orElseThrow();

//        try {
//            if (redisTemplate.opsForValue().get(user.getEmail()) != null) {
//                //delete refresh token
//                redisTemplate.delete(user.getEmail());
//            }
//        } catch (IllegalArgumentException e) {
//            logger.warn("user does not exist");
//        }

        logger.info(" logout ing : " + refreshToken);
        redisTemplate.expire(refreshToken,  24 * 60 * 60 * 7 * 1000, TimeUnit.MILLISECONDS);

        return new ResponseEntity(HttpStatus.OK);
    }


    @Override
    public TokenResponse GET_ACCESS_BY_REFRESH(String RefreshToken) {
        if (!jwtTokenUtil.isRefreshToken(RefreshToken)) throw new TokenInvalidException("토큰 에러뿅");
        String newtok = jwtTokenUtil.generateAccessToken(jwtTokenUtil.getId(RefreshToken));
        return new TokenResponse(newtok, RefreshToken);
    }
}
