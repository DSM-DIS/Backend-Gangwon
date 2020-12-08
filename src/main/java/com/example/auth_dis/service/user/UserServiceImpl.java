package com.example.auth_dis.service.user;

import com.example.auth_dis.Domain.user.User;
import com.example.auth_dis.Domain.user.UserRepository;
import com.example.auth_dis.Exception.*;
import com.example.auth_dis.jwt.JwtTokenUtil;
import com.example.auth_dis.paylod.UserInformationResponse;
import com.example.auth_dis.paylod.UserResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void SIGN_IN(UserResponse user) {
        String pw_role = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";
        String Id_role = ".{8,20}";
        String name_role = ".{1,20}";
        Matcher Id_matcher = Pattern.compile(Id_role).matcher(user.getEmail());
        Matcher password_matcher = Pattern.compile(pw_role).matcher(user.getPassword());
        Matcher name_matcher = Pattern.compile(name_role).matcher(user.getName());

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateIdException();
        } else if (userRepository.findByName(user.getName()).isPresent()) {
            throw new DuplicateNameException();
        } else if (!Id_matcher.matches()) {
            throw new IdTypeException();
        } else if ((!password_matcher.matches())) {
            throw new PasswordTypeException();
        } else if (!name_matcher.matches()) {
            throw new IdTypeException();
        } else {
            userRepository.save(
                    User.builder()
                            .email(user.getEmail())
                            .name(user.getName())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .build()
            );
        }
    }

    @Override
    public UserInformationResponse GET_INFO_BY_ACCESS(String AccessToken) {
        String email;
        try {
            email = jwtTokenUtil.getId(AccessToken);
        } catch (Exception e) {
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
