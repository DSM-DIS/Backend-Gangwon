package com.example.auth_dis.config;


import com.example.auth_dis.jwt.JwtAuthenticationEntryPoint;
import com.example.auth_dis.jwt.JwtRequestFilter;
import com.example.auth_dis.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                httpBasic().disable().
//                 security에서 기본으로 생성하는 login페이지 사용 안 함
        cors().and().
//                시큐리티에 cors 적용
        csrf().disable().
//                그중 CSRF(Cross Site Request Forgery : 사이트 간 요청 위조)
//                CSRF 프로텍션은 Http 세션과 동일한 생명주기(Life Cycle)을 가지는 토큰을 발행한 후 Http 요청
//                (PATCH, POST, PUT, DELETE 메소드인 경우)마다 발행된 토큰이 요청에 포함되어 있는지 검사하는
//                가장 일반적으로 알려진 방식의 구현이 설정되어 있습니다.
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//                스프링 시큐리티가 생성하지않고 기존것사용(jwt토큰)
        and().
                authorizeRequests().
                antMatchers("/**").permitAll().
                antMatchers("/user/**").hasAnyRole("USER").
                and().
                exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
                and().
//                apply(new JwtConfigurer(jwtTokenUtil));
                addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
