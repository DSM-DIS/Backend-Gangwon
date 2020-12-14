package com.example.auth_dis.Domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token,String> {
    Optional<Token> findByRefreshToken(String refreshToken);

}
