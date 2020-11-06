package com.example.auth_dis.service.auth;

import com.example.auth_dis.Domain.Account;
import com.example.auth_dis.Domain.Token;
import com.example.auth_dis.Exception3.*;
import com.example.auth_dis.Repository.AccountRepository;
import com.example.auth_dis.jwt.JwtTokenUtil;
import com.example.auth_dis.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager am;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    String pw_role = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";
    String Id_role = ".{8,20}";

    @Override
    public Map<String, Object> LOG_IN(Map<String, String> m) {
        final String email = m.get("email");

        logger.info("test input email: " + email);


        try {
            am.authenticate(new UsernamePasswordAuthenticationToken(email, m.get("password")));
        } catch (Exception e) {
            throw new NotFoundUserLoginException();
        }
        Account user = accountRepository.findByemail(email);
        String username = user.getUsername();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        Token retok = new Token();
        retok.setUsername(email);
        retok.setRefreshToken(refreshToken);

        //generate Token and save in redis
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(email, retok);

        logger.info("generated access token: " + accessToken);
        logger.info("generated refresh token: " + refreshToken);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }

    @Override
    public Map<String, Object> SIGN_IN(Account account) {

        String un = account.getUsername();
        String pw = account.getPassword();
        String em = account.getEmail();

        Matcher Id_matcher = Pattern.compile(Id_role).matcher(em);
        Matcher password_matcher = Pattern.compile(pw_role).matcher(pw);
        Map<String, Object> map = new HashMap<>();
        System.out.println(account);
        //이름 빈값
        try {
            if (un.trim().equals("") || un == null) {
                throw new NameNullException();
            } else if (accountRepository.findByemail(un) != null) {
                throw new DuplicateNameException();
            }

        } catch (NullPointerException e) {
            throw new NameNullException();
        }
        //아이디 빈값
        try {
            if (em.trim().equals("") || em == null) {
                throw new IdNullException();
            } else if (accountRepository.findByemail(em) != null) {
                throw new DuplicateIdException();
            } else if (!Id_matcher.matches()) {
                throw new IdTypeException();
            }

        } catch (NullPointerException e) {
            throw new IdNullException();
        }
        //비밀번호 빈값
        try {
            if (pw.trim().equals("") || pw == null) {
                throw new PasswordNullException();
            } else if (!password_matcher.matches()) {
                throw new PasswordTypeException();
            }
        } catch (NullPointerException e) {
            throw new PasswordNullException();

        }

        account.setUsername(un);
        account.setEmail(account.getEmail());
        account.setRole("ROLE_USER");
        account.setPassword(bcryptEncoder.encode(pw));
        accountRepository.save(account);
        map.put("message", true);

        return map;

    }

    @Override
    public Map<String, Object> GET_INFO_BY_ACCESS(String AccessToken) {
        String username = null;
        Map<String, Object> map = new HashMap<>();
        try {
            username = jwtTokenUtil.getUsernameFromToken(AccessToken);
        } catch (IllegalArgumentException e) {
            logger.warn("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
        } catch (SignatureException e) {
            throw new TokenInvalidException("토큰 값이 옳지 않음.");
        }
        logger.info(username);
        if (username != null) {
            map.put("message", true);
            map.put("username", username);
            Account user = accountRepository.findByUsername(username)
                    .orElseThrow();
            map.put("email", user.getEmail());
        } else {
            map.put("error", "Token null exception");
            map.put("message", "토큰의 값이 빈값입니다.");
        }
        return map;
    }

    public ResponseEntity<?> LOG_OUT(String RefreshToken) {
        String username = null;
        String refreshToken = RefreshToken;
        try {
            username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        }
//        catch (IllegalArgumentException e) {
//        }
        catch (ExpiredJwtException e) { //expire됐을 때
            username = e.getClaims().getSubject();
            logger.info("username from expired access token: " + username);
        }
        Account user = accountRepository.findByUsername(username)
                .orElseThrow();

        try {
            if (redisTemplate.opsForValue().get(user.getEmail()) != null) {
                //delete refresh token
                redisTemplate.delete(user.getEmail());
            }
        } catch (IllegalArgumentException e) {
            logger.warn("user does not exist");
        }

        //cache logout token for 10 minutes!
        logger.info(" logout ing : " + refreshToken);
//        redisTemplate.opsForValue().set(refreshToken, true);
        redisTemplate.expire(refreshToken, 10 * 6 * 1000, TimeUnit.MILLISECONDS);

        return new ResponseEntity(HttpStatus.OK);
    }

    public Map<String, Object> GET_ACCESS_BY_REFRESH(String RefreshToken) {
        String accessToken = null;
        String refreshToken = null;
        String refreshTokenFromDb = null;
        String username = null;
        Map<String, Object> map = new HashMap<>();
        try {
            refreshToken = RefreshToken;
            logger.info("refresh token in db: " + refreshToken);

            username = jwtTokenUtil.getUsernameFromToken(refreshToken);

            Account user = accountRepository.findByusername(username);
            String email = user.getEmail();

            try {
                ValueOperations<String, Object> vop = redisTemplate.opsForValue();
                Token result = null;
                result = (Token) vop.get(email);
                refreshTokenFromDb = result.getRefreshToken();
                logger.info("refresh token in db: " + refreshTokenFromDb);
                if (refreshToken.equals(refreshTokenFromDb)) {
                    logger.info("a");

                    final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    final String newtok = jwtTokenUtil.generateAccessToken(userDetails);
                    map.put("message", true);
                    map.put("accessToken", newtok);
                }
            } catch (IllegalArgumentException e) {
                logger.warn("illegal argument!!");
            }


        } catch (Exception e) {
            throw e;
        }

        return map;
    }
}
