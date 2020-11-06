package com.example.auth_dis.Repository;


import com.example.auth_dis.Domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByemail(String email);
    Account findByusername(String username);
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPassword(String password);

    Long deleteByUsername(String username);
}
