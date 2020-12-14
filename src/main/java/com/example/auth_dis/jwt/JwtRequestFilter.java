package com.example.auth_dis.jwt;

import com.example.auth_dis.Domain.user.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate<String, Object> redisTemplate;


    @Bean
    public FilterRegistrationBean JwtRequestFilterRegistration(JwtRequestFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            try {
                email = jwtTokenUtil.getId(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("Expired to get JWT Token");
            }
        }

        if (email == null) {
            logger.info("token maybe expired: username is null.");
        } else if (redisTemplate.opsForValue().get(jwtToken) != null) {
            logger.warn("this token already logout!");
        } else {
            //DB access 대신에 파싱한 정보로 유저 만들기!
            Authentication authen = jwtTokenUtil.getAuthentication(jwtToken);
            //만든 authentication 객체로 매번 인증받기
            SecurityContextHolder.getContext().setAuthentication(authen);
            String username = email;
            response.setHeader("username", username);
        }
        chain.doFilter(request, response);
    }
}
