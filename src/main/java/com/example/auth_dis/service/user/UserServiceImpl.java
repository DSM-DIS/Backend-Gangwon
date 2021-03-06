package com.example.auth_dis.service.user;

import com.example.auth_dis.Domain.user.User;
import com.example.auth_dis.Domain.user.UserRepository;
import com.example.auth_dis.Exception.*;
import com.example.auth_dis.jwt.JwtTokenUtil;
import com.example.auth_dis.paylod.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void SIGN_IN(UserRequest user) {
        String pw_role = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";
        String Id_role = ".{8,20}";
        String name_role = ".{1,20}";
        Matcher Id_matcher = Pattern.compile(Id_role).matcher(user.getId());
        Matcher password_matcher = Pattern.compile(pw_role).matcher(user.getPassword());
        Matcher name_matcher = Pattern.compile(name_role).matcher(user.getUsername());

        if (userRepository.findById(user.getId()).isPresent()) {
            System.out.println("아이디 중복");
            throw new DuplicateIdException();
        } else if (userRepository.findByName(user.getUsername()).isPresent()) {
            System.out.println("이름 중복");
            throw new DuplicateNameException();
        } else if (!Id_matcher.matches()) {
            System.out.println("아이디 형식에 맞지 않음");
            throw new IdTypeException();
        } else if ((!password_matcher.matches())) {
            System.out.println("비번 형식에 맞지 않음");
            throw new PasswordTypeException();
        } else if (!name_matcher.matches()) {
            System.out.println("이름 형식에 맞지 않음");
            throw new IdTypeException();
        } else {
            try {
                userRepository.save(
                        User.builder()
                                .email(user.getId())
                                .name(user.getUsername())
                                .password(passwordEncoder.encode(user.getPassword()))
                                .build()
                );
            } catch (Exception e) {
                System.out.println("값 비었음");
            }

        }
    }

    @Override
    public StatusResponse CheckId(IdResponse id) {
        String id2 = id.getId();
        String string;
        boolean status = userRepository.existsById(id2);
        if (status) {
            string = "false";
        } else {
            string = "true";
        }
        return StatusResponse.builder().status(string).build();
    }

    @Override
    public StatusResponse CheckUsername(NameResponse username) {
        String name = username.getUsername();
        String string;
        boolean status = userRepository.existsByName(name);
        if (status) {
            string = "false";
        } else {
            string = "true";
        }
        return StatusResponse.builder().status(string).build();
    }

    @Override
    public UserIdResponse GET_INFO_BY_ACCESS(String AccessToken) {
        String email;
        try {
            email = jwtTokenUtil.getId(AccessToken);
            System.out.println(email);
        } catch (Exception e) {
            System.out.println("토큰 값이 옳지 않음1");
            throw new TokenInvalidException("토큰 값이 옳지 않음.");
        }
        logger.info(email);
        if (email != null) {
            User user = userRepository.findById(email).orElseThrow();
            UserIdResponse userInformationResponse = new UserIdResponse(user.getEmail());
            return userInformationResponse;
        } else {
            System.out.println("토큰 값이 옳지 않음2");
            throw new TokenInvalidException("토큰 값이 옳지 않음.");
        }
    }

    @Override
    public UserNameResponse GetUserName(String userId) {

        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow();
            UserNameResponse userNameResponse = new UserNameResponse(user.getName());
            return userNameResponse;
        } else {
            System.out.println("토큰 값이 옳지 않음2");
            throw new TokenInvalidException("토큰 값이 옳지 않음.");
        }
    }
}
