package com.example.auth_dis.service;


import com.example.auth_dis.Domain.user.Account;
import com.example.auth_dis.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repository.findByemail(email);

        List<GrantedAuthority> roles = new ArrayList<>();

        if (account == null) {
            throw new UsernameNotFoundException("User not found with Id: " + email);
        }

        return new User(account.getUsername(), account.getPassword(), roles);
    }

}
